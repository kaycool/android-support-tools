package com.sirit.android.support.widget

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet

/**
 * @author kai.w
 * @des  $des
 */
class TitleToolBar : Toolbar {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }


    fun bindActivity(activity: AppCompatActivity) {
        activity.setSupportActionBar(this)
    }


    fun setTitleHeight() {

    }


    fun setLeftTitle() {

    }


    fun setTitle() {

    }

    fun setSubTitle() {

    }


    fun setRightTitle() {

    }

}


enum class TitleType {
    ICON, TEXT
}