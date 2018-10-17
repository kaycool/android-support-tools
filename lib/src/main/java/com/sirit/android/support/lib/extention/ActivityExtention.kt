package com.sirit.android.support.lib.extention

import android.app.Activity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.widget.help.ViewTitleHelp

/**
 * @author kai.w
 * @des  $des
 */

fun Activity.replaceToolbarLayout(toolbar: Toolbar, viewTitleHelp: ViewTitleHelp) {
    layoutInflater.inflate(R.layout.view_title, toolbar)

    val leftTitle = findViewById<TextView>(R.id.leftTitle)
    val centerLayout = findViewById<View>(R.id.centerLayout)
    val centerLeftTitle = findViewById<TextView>(R.id.centerLeftTitle)
    val subTitle = findViewById<TextView>(R.id.subTitle)
    val centerTitle = findViewById<TextView>(R.id.centerTitle)
    val rightTitle = findViewById<TextView>(R.id.rightTitle)

    toolbar.layoutParams.height = viewTitleHelp.mTitleHeight


    leftTitle?.apply {
        viewTitleHelp.mTitleLeftData?.let {

            it.titleText?.let {
                leftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.textSize.toFloat())
                leftTitle.setTextColor(color(it.textColor))

                (leftTitle.layoutParams as? RelativeLayout.LayoutParams?)
                    ?.setMargins(it.margin.left, it.margin.top, it.margin.right, it.margin.bottom)
            }
            it.titleIcon?.let {
                leftTitle.layoutParams.width = it.width
                leftTitle.layoutParams.height = it.height
                leftTitle.background = mipmapDrawable(it.iconRes)
            }
        }

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

