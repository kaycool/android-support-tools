package com.sirit.android.support.media

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


data class VideoBean(val videoPath: String
                     , var videoDuration: Double
                     , val videoTitle: String
                     , val videoSize: Long
                     , val videoName: String
                     , val videoWidth: Int
                     , val videoHeight: Int) : Serializable


data class PhotoBean(val path: String, var select: Boolean = false, val dir: String) : Serializable

class DirWithMedia : Serializable {

    var mDirWithPhotoMap = hashMapOf<String, ArrayList<PhotoBean>>()
    var mDirWithVideoMap = hashMapOf<String, ArrayList<VideoBean>>()
}

class DirMedia : Serializable {
    var dirName: ArrayList<String> = arrayListOf()
}

