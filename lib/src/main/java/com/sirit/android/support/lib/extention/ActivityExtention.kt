package com.sirit.android.support.lib.extention

import android.app.Activity
import android.support.v7.widget.Toolbar
import android.view.View
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.widget.help.ViewTitleHelp

/**
 * @author kai.w
 * @des  $des
 */

fun Activity.replaceToolbarLayout(toolbar: Toolbar, viewTitleHelp: ViewTitleHelp) {
    layoutInflater.inflate(R.layout.view_title, toolbar)

    val leftTitle = findViewById<View>(R.id.leftTitle)
    val centerLayout = findViewById<View>(R.id.centerLayout)
    val centerLeftTitle = findViewById<View>(R.id.centerLeftTitle)
    val subTitle = findViewById<View>(R.id.subTitle)
    val centerTitle = findViewById<View>(R.id.centerTitle)
    val rightTitle = findViewById<View>(R.id.rightTitle)

    toolbar.layoutParams.height = viewTitleHelp.mTitleHeight


    leftTitle?.apply {

    }


    centerLeftTitle?.apply {


    }


    subTitle?.apply {

    }


    centerTitle?.apply {

    }


    rightTitle?.apply {


    }

}

