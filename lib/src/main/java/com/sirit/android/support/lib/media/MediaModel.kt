package com.sirit.android.support.lib.media

import java.io.Serializable
import java.util.ArrayList

/**
 * @author kai.w
 * @des  $des
 */
data class MediaModel(var maxSelectedMediaCount: Int = 1, var allMediaCount: Int = Int.MAX_VALUE
                      , var currentMediaPosition: Int = 0, var spanCount: Int = 1
                      , var isShowCamera: Boolean = false, var isClickSelectable: Boolean = false
                      , var isShowGif: Boolean = true, var isShowVideo: Boolean = false) : Serializable


open class MediaBean(val filePath: String, val fileWidth: Int = 0
                     , val fileHeight: Int = 0, val fileSize: Long = 0
                     , val fileName: String = "", val fileTitle: String = ""
                     , var select: Boolean = false) : Serializable

data class VideoBean(val videoPath: String, val videoDuration: Double
                     , val videoWidth: Int, val videoHeight: Int
                     , val videoSize: Long, val videoName: String
                     , val videoTitle: String, var videoSelect: Boolean = false) {
    fun parseToMediaBean(): MediaBean = MediaBean(filePath = videoPath, fileWidth = videoWidth
        , fileHeight = videoHeight, fileSize = videoSize
        , fileName = videoName, fileTitle = videoTitle, select = videoSelect)
}

data class PhotoBean(val photoPath: String, val photoWidth: Int = 0, val photoHeight: Int = 0
                     , val photoDir: String, var photoSelect: Boolean = false) {
    fun parseToMediaBean(): MediaBean = MediaBean(filePath = photoPath, fileWidth = photoWidth
        , fileHeight = photoHeight, select = photoSelect)
}

class DirWithMedia : Serializable {
    var mDirWithPhotoMap = hashMapOf<String, MutableList<PhotoBean>>()
    var mDirWithVideoMap = hashMapOf<String, MutableList<VideoBean>>()
}

class DirMedia : Serializable {
    var dirName: ArrayList<String> = arrayListOf()
}

