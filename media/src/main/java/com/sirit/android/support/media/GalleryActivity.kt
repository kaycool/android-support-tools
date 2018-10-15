package com.sirit.android.support.media

import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.sirit.android.support.lib.compat.StatusBarCompat
import java.io.File
import java.util.*

/**
 * @author kai.w
 * @des  $des
 */
class GalleryActivity : AppCompatActivity() {
    val selectImages: MutableList<String> by lazy { mutableListOf<String>() }
    val selectVideos: MutableList<VideoBean> by lazy { mutableListOf<VideoBean>() }
    var groupMedia: DirWithMedia? = DirWithMedia()
        private set
    var dirMedia: DirMedia? = DirMedia()
        private set
    private var index = 0

    private val mediaModel by lazy { intent.getSerializableExtra(Params.MEDIA_MODEL) as? MediaModel? }
    private val mRvGallery by lazy { findViewById<RecyclerView>(R.id.rvGallery) }
    private val mGalleryAdapter by lazy { GalleryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        StatusBarCompat.compat(this)


        mRvGallery.apply {
            mediaModel?.let {
                this.layoutManager = GridLayoutManager(this@GalleryActivity, it.spanCount)
                this.adapter = GalleryAdapter(this@GalleryActivity)
                startImages(true, contentResolver)
            }
        }

    }


    fun startImages(showGif: Boolean, resolver: ContentResolver) {
        clear()
        imageThread(showGif, resolver).start()
    }

    fun startVideo(resolver: ContentResolver) {
        clear()
        videoThread(resolver).start()
    }

    fun stop() {
        clear()
    }


    private fun clear() {
        selectImages.clear()
        groupMedia?.mDirWithPhotoMap?.clear()
        groupMedia?.mDirWithVideoMap?.clear()
        dirMedia?.dirName?.clear()
        index = 0
    }

    private fun imageThread(showGif: Boolean, resolver: ContentResolver): Thread {
        return Thread(Runnable {
            val mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            //jpeg & png & gif & video
            val mCursor: Cursor?
            mCursor = if (showGif) {
                resolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                    arrayOf("image/jpeg", "image/png", "image/gif"), MediaStore.Images.Media.DATE_MODIFIED + " desc")//"image/x-ms-bmp"
            } else {
                resolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                    arrayOf("image/jpeg", "image/png"), MediaStore.Images.Media.DATE_MODIFIED + " desc")
            }

            if (mCursor == null) {
                return@Runnable
            }

            val mGroupMap = LinkedHashMap<String, MutableList<PhotoBean>>()
            val dirNames = ArrayList<String>()
            while (mCursor.moveToNext()) {
                // get image path
                val path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA))

                val imgWidth = mCursor.getInt(mCursor
                    .getColumnIndex(MediaStore.Images.Media.WIDTH))

                val imgHeight = mCursor.getInt(mCursor
                    .getColumnIndex(MediaStore.Images.Media.HEIGHT))

                val file = File(path)
                if (!file.exists()) {
                    continue
                }

                // get image parent name
                val parentName = File(path).parentFile.name
                // save all Photo
                if (!mGroupMap.containsKey(ALL_PHOTOS)) {
                    dirNames.add(ALL_PHOTOS)
                    val chileList = ArrayList<PhotoBean>()
                    chileList.add(PhotoBean(path, imgWidth, imgHeight, ALL_PHOTOS))
                    mGroupMap[ALL_PHOTOS] = chileList
                } else {
                    mGroupMap[ALL_PHOTOS]?.add(PhotoBean(path, imgWidth, imgHeight, ALL_PHOTOS))
                }
                // save by parent name
                if (!mGroupMap.containsKey(parentName)) {
                    dirNames.add(parentName)
                    val chileList = ArrayList<PhotoBean>()
                    chileList.add(PhotoBean(path, imgWidth, imgHeight, parentName))
                    mGroupMap[parentName] = chileList
                } else {
                    mGroupMap[parentName]?.add(PhotoBean(path, imgWidth, imgHeight, parentName))
                }

                // 每100条发送一次event
                if (mGroupMap[ALL_PHOTOS]?.size?.rem(100) == 0) {
                    refreshImages(mGroupMap)
                }

            }
            mCursor.close()
            this.dirMedia?.dirName = dirNames
            this.groupMedia?.mDirWithPhotoMap = mGroupMap
            refreshImages(mGroupMap, finish = true)
        })
    }


    fun refreshImages(groupMap: LinkedHashMap<String, MutableList<PhotoBean>>, finish: Boolean = false) {
        runOnUiThread {
            groupMap[ALL_PHOTOS]?.let {
                mGalleryAdapter.resetData(mutableListOf<MediaBean>().apply {
                    it.forEach {
                        this.add(it.parseToMediaBean())
                    }
                })
            }
        }
    }


    private fun videoThread(resolver: ContentResolver): Thread {
        return Thread(Runnable {
            val mVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Video.Media._ID
                , MediaStore.Video.Media.DISPLAY_NAME
                , MediaStore.Video.Media.DATA
                , MediaStore.Video.Media.DURATION
                , MediaStore.Video.Media.TITLE
                , MediaStore.Video.Media.SIZE
                , MediaStore.Video.Media.WIDTH
                , MediaStore.Video.Media.HEIGHT)
            val orderBy = MediaStore.Video.Media.DISPLAY_NAME
            val mCursor = resolver.query(mVideoUri, projection, null, null, orderBy)
                ?: return@Runnable

            val mGroupMap = LinkedHashMap<String, MutableList<VideoBean>>()
            val dirNames = ArrayList<String>()
            while (mCursor.moveToNext()) {
                val videoPath = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Video.Media.DATA))

                val videoDuration = mCursor.getDouble(mCursor
                    .getColumnIndex(MediaStore.Video.Media.DURATION)) / 1000
                if (videoDuration <= 0) {
                    continue
                }

                val videoTitle = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Video.Media.TITLE))

                val videoSize = mCursor.getLong(mCursor
                    .getColumnIndex(MediaStore.Video.Media.SIZE))

                val videoWidth = mCursor.getInt(mCursor
                    .getColumnIndex(MediaStore.Video.Media.WIDTH))

                val videoHeight = mCursor.getInt(mCursor
                    .getColumnIndex(MediaStore.Video.Media.HEIGHT))

                val file = File(videoPath)
                if (!file.exists()) {
                    continue
                }
                // get video parent name
                val parentName = File(videoPath).parentFile.name
                val fileName = file.name

                if (!mGroupMap.containsKey(ALL_VIDEOS)) {
                    dirNames.add(ALL_VIDEOS)
                    val chileList = ArrayList<VideoBean>()
                    chileList.add(VideoBean(videoPath, videoDuration, videoWidth, videoHeight, videoSize, videoTitle, fileName))
                    mGroupMap[ALL_VIDEOS] = chileList
                } else {
                    mGroupMap[ALL_VIDEOS]?.add(VideoBean(videoPath, videoDuration, videoWidth, videoHeight, videoSize, videoTitle, fileName))
                }

                // save by parent name
                if (!mGroupMap.containsKey(parentName)) {
                    dirNames.add(parentName)
                    val chileList = ArrayList<VideoBean>()
                    chileList.add(VideoBean(videoPath, videoDuration, videoWidth, videoHeight, videoSize, videoTitle, fileName))
                    mGroupMap[parentName] = chileList
                } else {
                    mGroupMap[parentName]?.add(VideoBean(videoPath, videoDuration, videoWidth, videoHeight, videoSize, videoTitle, fileName))
                }

                // 每100条发送一次event
                if (mGroupMap[ALL_VIDEOS]?.size?.rem(100) == 0) {
                    refreshVideo(mGroupMap)
                }
            }
            mCursor.close()

            this.dirMedia?.dirName = dirNames
            this.groupMedia?.mDirWithVideoMap = mGroupMap
            refreshVideo(mGroupMap, finish = true)
        })
    }


    fun refreshVideo(groupMap: LinkedHashMap<String, MutableList<VideoBean>>, finish: Boolean = false) {
        runOnUiThread {
            groupMap[ALL_PHOTOS]?.let {
                mGalleryAdapter.resetData(mutableListOf<MediaBean>().apply {
                    it.forEach {
                        this.add(it.parseToMediaBean())
                    }
                })
            }
        }
    }


    companion object {
        // all photos
        val ALL_PHOTOS = "All Photos"
        // all videos
        val ALL_VIDEOS = "All Videos"
    }

}