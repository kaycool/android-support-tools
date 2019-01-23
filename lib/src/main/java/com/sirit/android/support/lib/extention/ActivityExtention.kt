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
import com.gyf.barlibrary.ImmersionBar
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
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}


/**
 * 状态栏亮色模式，设置状态栏黑色文字、图标，
 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
 *
 * @param activity
 * @return 1:MIUUI 2:Flyme 3:android6.0
 */
fun Activity.statusBarLightMode(): Int {
    var result = 0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        when {
            MIUISetStatusBarLightMode(true) -> //小米
                result = 1
            FlymeSetStatusBarLightMode(true) -> //魅族
                result = 2
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                //6.0以上
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                result = 3
            }
            else -> //其他的都设置状态栏成半透明的,以下设置半透明是调用第三方ImmersionBar库的，根据个人需求更改，
                ImmersionBar.with(this).statusBarDarkFont(true, 0.5f).init()
        }
    }
    return result
}

/**
 * 设置状态栏图标为深色和魅族特定的文字风格
 * 可以用来判断是否为Flyme用户
 *
 * @param window 需要设置的窗口
 * @param dark   是否把状态栏文字及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
fun Activity.FlymeSetStatusBarLightMode(dark: Boolean): Boolean {
    var result = false
    if (window != null) {
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
        } catch (e: Exception) {

        }

    }
    return result
}

/**
 * 需要MIUIV6以上
 *
 * @param activity
 * @param dark     是否把状态栏文字及图标颜色设置为深色
 * @return boolean 成功执行返回true
 */
fun Activity.MIUISetStatusBarLightMode(dark: Boolean): Boolean {
    var result = false
    val window = window
    if (window != null) {
        val clazz = window.javaClass
        try {
            var darkModeFlag = 0
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
            result = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (dark) {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                }
            }
        } catch (e: Exception) {

        }

    }
    return result
}


fun Activity.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}


interface TitleClick {
    fun titleClick(titleClickEnum: TitleClickEnum, clickView: View)
}

enum class TitleClickEnum {
    LEFT, CENTER, RIGHT
}

