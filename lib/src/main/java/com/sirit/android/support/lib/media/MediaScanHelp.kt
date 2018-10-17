package com.sirit.android.support.lib.media

import android.content.ContentResolver
import android.database.Cursor
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * @author kai.w
 * @des  $des
 */
object MediaScanHelp {
    // all photos
    val ALL_PHOTOS = "All Photos"
    // all videos
    val ALL_VIDEOS = "All Videos"

    private var groupMedia: DirWithMedia? = DirWithMedia()
    private var dirMedia: DirMedia? = DirMedia()
    private var index = 0

    private var mDataCallback: MediaScanCallback? = null

    fun registerMediaScanCallback(dataCallback: MediaScanCallback): MediaScanHelp {
        this.mDataCallback = dataCallback
        return this
    }

    fun unRegisterMediaScanCallback(): MediaScanHelp {
        this.mDataCallback = null
        return this
    }

    fun scanImages(showGif: Boolean, resolver: ContentResolver) {
        clear()
        imageThread(showGif, resolver).start()
        Log.d("MediaHelp", "MediaHelp image start")
    }

    fun scanVideo(resolver: ContentResolver) {
        clear()
        videoThread(resolver).start()
        Log.d("MediaHelp", "MediaHelp video start")
    }

    fun stop() {
        clear()
        Log.d("MediaHelp", "PickPhotoHelper stop")
    }

    fun clear() {
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
                    arrayOf("image/jpeg", "image/png", "image/gif")
                    , MediaStore.Images.Media.DATE_MODIFIED + " desc")//"image/x-ms-bmp"
            } else {
                resolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                    arrayOf("image/jpeg", "image/png")
                    , MediaStore.Images.Media.DATE_MODIFIED + " desc")
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
                    refreshImages()
                }

            }
            mCursor.close()
            dirMedia?.dirName = dirNames
            groupMedia?.mDirWithPhotoMap = mGroupMap
            refreshImages(finish = true)
        })
    }


    private fun refreshImages(finish: Boolean = false) {
        Handler(Looper.getMainLooper()).post {
            if (finish) {
                mDataCallback?.mediaCallback(dirMedia, groupMedia)
            } else {
                mDataCallback?.mediaCallback(DirMedia().apply { this.dirName.add(ALL_PHOTOS) }, groupMedia)
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
                    refreshVideo()
                }
            }
            mCursor.close()

            dirMedia?.dirName = dirNames
            groupMedia?.mDirWithVideoMap = mGroupMap
            refreshVideo(true)
        })
    }


    private fun refreshVideo(finish: Boolean = false) {
        Handler(Looper.getMainLooper()).post {
            if (finish) {
                mDataCallback?.mediaCallback(dirMedia, groupMedia)
            } else {
                mDataCallback?.mediaCallback(DirMedia().apply { this.dirName.add(ALL_VIDEOS) }, groupMedia)
            }
        }
    }
}


interface MediaScanCallback {
    fun mediaCallback(dirMedia: DirMedia?, groupMedia: DirWithMedia?)
}