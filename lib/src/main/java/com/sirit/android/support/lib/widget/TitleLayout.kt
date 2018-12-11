package com.sirit.android.support.lib.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.extention.mipmapDrawable
import kotlinx.android.synthetic.main.view_title.view.*

/**
 * @author kai.w
 *      ${DES}
 */
class TitleLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        LayoutInflater.from(context).inflate(R.layout.view_title, this, true)
    }


    fun setLeftTitle(textSize: Float, textColor: Int, titleText: String, onClickListener: OnClickListener? = null) {
        leftTitle.text = titleText
        leftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        leftTitle.setTextColor(textColor)
        rlLeftArea.setOnClickListener(onClickListener)
    }

    fun setLeftIcon(iconWidth: Int, iconHeight: Int, drawableId: Int, onClickListener: OnClickListener? = null) {
        leftTitle.layoutParams.width = iconWidth
        leftTitle.layoutParams.height = iconHeight
        leftTitle.background = context.mipmapDrawable(drawableId)
        rlLeftArea.setOnClickListener(onClickListener)
    }

    fun setCenterLeftTitle(textSize: Float, textColor: Int, titleText: String) {
        centerLeftTitle.visibility = View.VISIBLE
        centerLeftTitle.text = titleText
        centerLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        centerLeftTitle.setTextColor(textColor)
    }

    fun setCenterLeftIcon(iconWidth: Int, iconHeight: Int, drawableId: Int) {
        centerLeftTitle.visibility = View.VISIBLE
        centerLeftTitle.layoutParams.width = iconWidth
        centerLeftTitle.layoutParams.height = iconHeight
        centerLeftTitle.background = context.mipmapDrawable(drawableId)
    }

    fun setSubTitle(textSize: Float, textColor: Int, titleText: String) {
        subTitle.visibility = View.VISIBLE
        subTitle.text = titleText
        subTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        subTitle.setTextColor(textColor)
    }

    fun setSubIcon(iconWidth: Int, iconHeight: Int, drawableId: Int) {
        subTitle.visibility = View.VISIBLE
        subTitle.layoutParams.width = iconWidth
        subTitle.layoutParams.height = iconHeight
        subTitle.background = context.mipmapDrawable(drawableId)
    }

    fun setCenterTitle(textSize: Float, textColor: Int, titleText: String, isBold: Boolean = false) {
        centerTitle.text = titleText
        centerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        centerTitle.setTextColor(textColor)
        centerTitle.paint.isFakeBoldText = isBold
    }

    fun setCenterIcon(iconWidth: Int, iconHeight: Int, drawableId: Int) {
        centerTitle.layoutParams.width = iconWidth
        centerTitle.layoutParams.height = iconHeight
        centerTitle.background = context.mipmapDrawable(drawableId)
    }

    fun setRightTitle(textSize: Float, textColor: Int, titleText: String, onClickListener: OnClickListener? = null) {
        rightTitle.text = titleText
        rightTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        rightTitle.setTextColor(textColor)
        rlRightArea.setOnClickListener(onClickListener)
    }

    fun setRightIcon(iconWidth: Int, iconHeight: Int, drawableId: Int, onClickListener: OnClickListener? = null) {
        rightTitle.layoutParams.width = iconWidth
        rightTitle.layoutParams.height = iconHeight
        rightTitle.background = context.mipmapDrawable(drawableId)
        rlRightArea.setOnClickListener(onClickListener)
    }

    fun showLine(lineColor: Int, lineHeight: Int) {
        viewTitleLine.visibility = View.VISIBLE
        viewTitleLine.setBackgroundColor(lineColor)
        viewTitleLine.layoutParams.height = lineHeight
    }

}