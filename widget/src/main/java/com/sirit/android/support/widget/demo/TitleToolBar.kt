package com.sirit.android.support.widget.demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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