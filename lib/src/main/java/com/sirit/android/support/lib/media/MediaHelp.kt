package com.sirit.android.support.lib.media

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.io.Serializable

/**
 * @author kai.w
 */
class MediaHelp private constructor(builder: Build) {
    private final val ctx: Context by lazy { builder.ctx }
    private final val mSpanCount: Int by lazy { builder.mSpanCount }
    private final val mMaxSelected: Int by lazy { builder.mMaxSelected }
    private final val mMaxShowCount: Int by lazy { builder.mMaxShowCount }
    private final val mIsShowGift: Boolean by lazy { builder.mIsShowGift }
    private final val mIsShowCamera: Boolean by lazy { builder.mIsShowCamera }


    fun showImages() {
        val intent = Intent(ctx, GalleryActivity::class.java)
        (ctx as? Activity)?.let {
            it.startActivity(intent)
        }
    }


    fun showVideos() {
        val intent = Intent(ctx, GalleryActivity::class.java)
        (ctx as? Activity)?.let {
            it.startActivity(intent)
        }
    }


    companion object {

        open class Build(val ctx: Context) {
            var mSpanCount: Int = 1
            var mMaxSelected: Int = 1
            var mMaxShowCount: Int = Int.MAX_VALUE
            var mIsShowGift: Boolean = true
            var mIsShowCamera: Boolean = true
            var mediaDataCallback: MediaDataCallback? = null

            fun setSpanCount(spanCount: Int): Build {
                this.mSpanCount = spanCount
                return this
            }

            fun setMaxSelected(maxSelected: Int): Build {
                this.mMaxSelected = maxSelected
                return this
            }

            fun setMaxShowCount(maxShowCount: Int): Build {
                this.mMaxShowCount = maxShowCount
                return this
            }

            fun setIsShowGift(isShowGift: Boolean): Build {
                this.mIsShowGift = isShowGift
                return this
            }

            fun setIsShowCamera(isShowCamera: Boolean): Build {
                this.mIsShowCamera = isShowCamera
                return this
            }


            fun build(): MediaHelp = MediaHelp(this)
        }
    }
}


interface MediaDataCallback : Serializable {
    fun mediaCallback(mediaList: MutableList<MediaBean>)
}


