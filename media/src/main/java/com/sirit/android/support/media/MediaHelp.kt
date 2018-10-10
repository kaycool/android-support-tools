package com.sirit.android.support.media

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * @author kai.w
 */
object MediaHelp {

    class Builder(private val ctx: Context) {
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

}