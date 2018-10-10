package com.sirit.android.support.lib.compat

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


/**
 * @author kai.w
 * @des  $des
 */
object StatusBarCompat {

    fun compat(activity: Activity, color: Int = ContextCompat.getColor(activity, android.R.color.holo_blue_light)) {
        val window = activity.window

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                //设置状态栏颜色
                window.statusBarColor = color
                //设置导航栏颜色
                window.navigationBarColor = color
                val contentView = activity.findViewById(android.R.id.content) as ViewGroup
                val childAt = contentView.getChildAt(0)
                if (childAt != null) {
                    childAt.fitsSystemWindows = true
                }
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                //透明导航栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                //设置contentview为fitsSystemWindows
                val contentView = activity.findViewById(android.R.id.content) as ViewGroup
                val childAt = contentView.getChildAt(0)
                if (childAt != null) {
                    childAt.fitsSystemWindows = true
                }
                //给statusbar着色
                val view = View(activity)
                view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
                view.setBackgroundColor(color)
                contentView.addView(view)
            }
        }
    }

    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    fun getNavigationBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

}