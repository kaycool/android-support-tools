package com.sirit.android.support.media

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.sirit.android.support.extention.openSystemCamera
import java.io.File
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * @author kai.w
 */
object MediaHelp {

    val selectImages: MutableList<String> by lazy { mutableListOf<String>() }
    val selectVideos: MutableList<VideoBean> by lazy { mutableListOf<VideoBean>() }
    var groupMedia: DirWithMedia? = DirWithMedia()
        private set
    var dirMedia: DirMedia? = DirMedia()
        private set
    private var index = 0

    class Builder(val ctx: Context) {
        private val mediaModel = MediaModel()

        fun setMaxSelectedMediaCount(maxSelectedMediaCount: Int): Builder {
            mediaModel.maxSelectedMediaCount = maxSelectedMediaCount
            return this
        }

        fun setAllMediaCount(allMediaCount: Int): Builder {
            mediaModel.allMediaCount = allMediaCount
            return this
        }

        fun setCurrentMediaPosition(currentMediaPosition: Int): Builder {
            mediaModel.currentMediaPosition = currentMediaPosition
            return this
        }

        fun setSpanCount(spanCount: Int): Builder {
            mediaModel.spanCount = spanCount
            return this
        }

        fun setIsShowCamera(isShowCamera: Boolean): Builder {
            mediaModel.isShowCamera = isShowCamera
            return this
        }

        fun setIsClickSelectable(isClickSelectable: Boolean): Builder {
            mediaModel.isClickSelectable = isClickSelectable
            return this
        }

        fun setIsShowGif(isShowGif: Boolean): Builder {
            mediaModel.isShowGif = isShowGif
            return this
        }

        fun setIsShowVideo(isShowVideo: Boolean): Builder {
            mediaModel.isShowVideo = isShowVideo
            return this
        }

        fun startGallery() {
            val intent = Intent(ctx, GalleryActivity::class.java)
            intent.putExtra(Params.MEDIA_MODEL, mediaModel)
            (ctx as? Activity?)?.startActivity(intent)
        }
    }



    fun startImages(showGif: Boolean, resolver: ContentResolver) {
        clear()
        imageThread(showGif, resolver).start()
        Log.d("PickPhotoView", "PickPhotoHelper image start")
    }

    fun startVideo(resolver: ContentResolver) {
        clear()
        videoThread(resolver).start()
        Log.d("PickPhotoView", "PickPhotoHelper video start")
    }

    fun stop() {
        clear()
        Log.d("PickPhotoView", "PickPhotoHelper stop")
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

            val mGroupMap = LinkedHashMap<String, ArrayList<PhotoBean>>()
            val dirNames = ArrayList<String>()
            while (mCursor.moveToNext()) {
                // get image path
                val path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA))

                val file = File(path)
                if (!file.exists()) {
                    continue
                }

                // get image parent name
                val parentName = File(path).parentFile.name
                // save all Photo
//                if (!mGroupMap.containsKey(PickConfig.ALL_PHOTOS)) {
//                    dirNames.add(PickConfig.ALL_PHOTOS)
//                    val chileList = ArrayList<PhotoBean>()
//                    chileList.add(PhotoBean(path, false, PickConfig.ALL_PHOTOS))
//                    mGroupMap[PickConfig.ALL_PHOTOS] = chileList
//                } else {
//                    mGroupMap[PickConfig.ALL_PHOTOS]?.add(PhotoBean(path, false, PickConfig.ALL_PHOTOS))
//                }
                // save by parent name
                if (!mGroupMap.containsKey(parentName)) {
                    dirNames.add(parentName)
                    val chileList = ArrayList<PhotoBean>()
                    chileList.add(PhotoBean(path, false, parentName))
                    mGroupMap[parentName] = chileList
                } else {
                    mGroupMap[parentName]?.add(PhotoBean(path, false, parentName))
                }

                // 每100条发送一次event
//                if (mGroupMap[PickConfig.ALL_PHOTOS]?.size?.rem(100) == 0) {
//                    sendImages(mGroupMap)
//                }

            }
            mCursor.close()
            this.dirMedia?.dirName = dirNames
            this.groupMedia?.mDirWithPhotoMap = mGroupMap
//            sendImages(mGroupMap, finish = true)
        })
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

            val mGroupMap = LinkedHashMap<String, ArrayList<VideoBean>>()
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

//                if (!mGroupMap.containsKey(PickConfig.ALL_VIDEOS)) {
//                    dirNames.add(PickConfig.ALL_VIDEOS)
//                    val chileList = ArrayList<VideoBean>()
//                    chileList.add(VideoBean(videoPath, videoDuration, videoTitle, videoSize, fileName, videoWidth, videoHeight))
//                    mGroupMap[PickConfig.ALL_VIDEOS] = chileList
//                } else {
//                    mGroupMap[PickConfig.ALL_VIDEOS]?.add(VideoBean(videoPath, videoDuration, videoTitle, videoSize, fileName, videoWidth, videoHeight))
//                }

                // save by parent name
                if (!mGroupMap.containsKey(parentName)) {
                    dirNames.add(parentName)
                    val chileList = ArrayList<VideoBean>()
                    chileList.add(VideoBean(videoPath, videoDuration, videoTitle, videoSize, fileName, videoWidth, videoHeight))
                    mGroupMap[parentName] = chileList
                } else {
                    mGroupMap[parentName]?.add(VideoBean(videoPath, videoDuration, videoTitle, videoSize, fileName, videoWidth, videoHeight))
                }

                // 每100条发送一次event
//                if (mGroupMap[PickConfig.ALL_VIDEOS]?.size?.rem(100) == 0) {
//                    sendVideos(mGroupMap)
//                }
            }
            mCursor.close()

            this.dirMedia?.dirName = dirNames
            this.groupMedia?.mDirWithVideoMap = mGroupMap
//            sendVideos(mGroupMap, finish = true)
        })
    }


}