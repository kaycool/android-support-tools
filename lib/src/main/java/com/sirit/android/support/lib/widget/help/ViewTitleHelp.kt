package com.sirit.android.support.lib.widget.help

import android.graphics.Rect
import java.io.Serializable

/**
 * @author kai.w
 * @des  $des
 */
class ViewTitleHelp(builder: ViewTitleBuilder) {
    val mTitleHeight: Int by lazy { builder.mTitleHeight }
    val mTitleLeftData: TitleData? by lazy { builder.mTitleLeftData }
    val mTitleCenterLeftData: TitleData? by lazy { builder.mTitleCenterLeftData }
    val mTitleSubData: TitleData? by lazy { builder.mTitleSubData }
    val mTitleCenterData: TitleData? by lazy { builder.mTitleCenterData }
    val mTitleRightData: TitleData? by lazy { builder.mTitleRightData }

    companion object {
        class ViewTitleBuilder {
            var mTitleHeight: Int = 0
            var mTitleLeftData: TitleData? = null
            var mTitleCenterLeftData: TitleData? = null
            var mTitleSubData: TitleData? = null
            var mTitleCenterData: TitleData? = null
            var mTitleRightData: TitleData? = null

            fun setTitleHeight(titleHeight: Int): ViewTitleBuilder {
                this.mTitleHeight = titleHeight
                return this
            }

            fun setTitleLeftData(titleLeftData: TitleData): ViewTitleBuilder {
                this.mTitleLeftData = titleLeftData
                return this
            }

            fun setTitleCenterLeftData(titleCenterLeftData: TitleData): ViewTitleBuilder {
                this.mTitleCenterLeftData = titleCenterLeftData
                return this
            }

            fun setTitleSubData(titleSubData: TitleData): ViewTitleBuilder {
                this.mTitleSubData = titleSubData
                return this
            }


            fun setTitleCenterData(titleCenterData: TitleData): ViewTitleBuilder {
                this.mTitleCenterData = titleCenterData
                return this
            }

            fun setTitleRightData(titleRightData: TitleData): ViewTitleBuilder {
                this.mTitleRightData = titleRightData
                return this
            }


            fun build() = ViewTitleHelp(this)
        }


        data class TitleData(val titleText: TitleText? = null, val titleIcon: TitleIcon? = null) : Serializable

        data class TitleText(val textSize: Int, val textColor: Int, val margin: Rect) : Serializable

        data class TitleIcon(val iconRes: Int, val width: Int, val height: Int, val margin: Rect) : Serializable

    }

}