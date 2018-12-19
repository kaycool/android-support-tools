package com.sirit.android.support.lib.extention

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.widget.help.ViewTitleHelp


/**
 * @author kai.w
 * @des  $des
 */

fun AppCompatActivity.replaceToolbarLayout(toolbar: Toolbar, viewTitleHelp: ViewTitleHelp, titleClick: TitleClick? = null) {
    setSupportActionBar(toolbar)
    layoutInflater.inflate(R.layout.view_title, toolbar)

    val leftTitle = findViewById<TextView>(R.id.leftTitle)
    val centerLayout = findViewById<View>(R.id.centerLayout)
    val centerLeftTitle = findViewById<TextView>(R.id.centerLeftTitle)
    val subTitle = findViewById<TextView>(R.id.subTitle)
    val centerTitle = findViewById<TextView>(R.id.centerTitle)
    val rightTitle = findViewById<TextView>(R.id.rightTitle)
    val viewTitleLine = findViewById<View>(R.id.viewTitleLine)
    val rlLeftArea = findViewById<View>(R.id.rlLeftArea)
    val rlRightArea = findViewById<View>(R.id.rlRightArea)

    if (viewTitleHelp.mTitleHeight > 0) {
        toolbar.layoutParams.height = viewTitleHelp.mTitleHeight
    }

    leftTitle?.apply {
        viewTitleHelp.mTitleLeftData?.let {

            it.titleText?.let {
                leftTitle.text = it.titleText
                leftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.textSize.toFloat())
                leftTitle.setTextColor(it.textColor)

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
        viewTitleHelp.mTitleCenterLeftData?.let {

            it.titleText?.let {
                centerLeftTitle.text = it.titleText
                centerLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.textSize.toFloat())
                centerLeftTitle.setTextColor(it.textColor)

                (centerLeftTitle.layoutParams as? RelativeLayout.LayoutParams?)
                        ?.setMargins(it.margin.left, it.margin.top, it.margin.right, it.margin.bottom)
            }
            it.titleIcon?.let {
                centerLeftTitle.layoutParams.width = it.width
                centerLeftTitle.layoutParams.height = it.height
                centerLeftTitle.background = mipmapDrawable(it.iconRes)
            }

        }
    }


    subTitle?.apply {
        viewTitleHelp.mTitleSubData?.let {
            it.titleText?.let {
                subTitle.text = it.titleText
                subTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.textSize.toFloat())
                subTitle.setTextColor(it.textColor)

                (subTitle.layoutParams as? RelativeLayout.LayoutParams?)
                        ?.setMargins(it.margin.left, it.margin.top, it.margin.right, it.margin.bottom)
            }
            it.titleIcon?.let {
                subTitle.layoutParams.width = it.width
                subTitle.layoutParams.height = it.height
                subTitle.background = mipmapDrawable(it.iconRes)
            }
        }
    }


    centerTitle?.apply {
        viewTitleHelp.mTitleCenterData?.let {
            it.titleText?.let {
                centerTitle.text = it.titleText
                centerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.textSize.toFloat())
                centerTitle.setTextColor(it.textColor)

                (centerTitle.layoutParams as? RelativeLayout.LayoutParams?)
                        ?.setMargins(it.margin.left, it.margin.top, it.margin.right, it.margin.bottom)
            }
            it.titleIcon?.let {
                centerTitle.layoutParams.width = it.width
                centerTitle.layoutParams.height = it.height
                centerTitle.background = mipmapDrawable(it.iconRes)
            }
        }
    }


    rightTitle?.apply {
        viewTitleHelp.mTitleRightData?.let {
            it.titleText?.let {
                rightTitle.text = it.titleText
                rightTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.textSize.toFloat())
                rightTitle.setTextColor(it.textColor)

                (rightTitle.layoutParams as? RelativeLayout.LayoutParams?)
                        ?.setMargins(it.margin.left, it.margin.top, it.margin.right, it.margin.bottom)
            }
            it.titleIcon?.let {
                rightTitle.layoutParams.width = it.width
                rightTitle.layoutParams.height = it.height
                rightTitle.background = mipmapDrawable(it.iconRes)
            }
        }
    }

    viewTitleLine?.apply {
        viewTitleHelp.mTitleLineData?.let {
            this.visibility = View.VISIBLE
            this.setBackgroundColor(it.lineColor)
            this.layoutParams.height = it.lineHeight
        }
    }

    rlLeftArea.setOnClickListener { titleClick?.titleClick(TitleClickEnum.LEFT, it) }
    rlRightArea.setOnClickListener { titleClick?.titleClick(TitleClickEnum.RIGHT, it) }
    centerLeftTitle.setOnClickListener { titleClick?.titleClick(TitleClickEnum.CENTER, it) }
}


fun Activity.transparentStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.TRANSPARENT
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val localLayoutParams = window.attributes
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }else {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}


interface TitleClick {
    fun titleClick(titleClickEnum: TitleClickEnum, clickView: View)
}

enum class TitleClickEnum {
    LEFT, CENTER, RIGHT
}

