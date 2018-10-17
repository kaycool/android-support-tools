package com.sirit.android.support.lib.widget.help

/**
 * @author kai.w
 * @des  $des
 */
class ViewTitleHelp(viewTitleBuild: ViewTitleBuild) {
    val mTitleHeight: Int by lazy { viewTitleBuild.mTitleHeight }

    companion object {
        class ViewTitleBuild {
            var mTitleHeight: Int = 0


            fun setTitleHeight(titleHeight: Int): ViewTitleBuild {
                this.mTitleHeight = titleHeight
                return this
            }


            fun build() = ViewTitleHelp(this)
        }


//        data class View

        enum class TitleType {
            TEXT, ICON
        }
    }

}