package com.sirit.android.support.lib.compat

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout


/**
 * Created by wanbo on 2017/11/8.
 */
class AndroidBug5497Workaround(activity: Activity) {

    private var mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private var frameLayoutParams: FrameLayout.LayoutParams

    //为适应华为小米等手机键盘上方出现黑条或不适配
    private var contentHeight: Int = 0//获取setContentView本来view的高度
    private var isfirst = true//只用获取一次
    private var statusBarHeight: Int = 0//状态栏高度

    init {
        //1､找到Activity的最外层布局控件，它其实是一个DecorView,它所用的控件就是FrameLayout
        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        //2､获取到setContentView放进去的View
        mChildOfContent = content.getChildAt(0)
        //3､给Activity的xml布局设置View树监听，当布局有变化，如键盘弹出或收起时，都会回调此监听
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener {
            if (isfirst) {
                contentHeight = mChildOfContent.height//兼容华为等机型
                isfirst = false
            }
            //5､当前布局发生变化时，对Activity的xml布局进行重绘
            possiblyResizeChildOfContent()
        }
        //6､获取到Activity的xml布局的放置参数
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    companion object {

        fun assistActivity(activity: Activity) {
            AndroidBug5497Workaround(activity)
        }
    }

    // 获取界面可用高度，如果软键盘弹起后，Activity的xml布局可用高度需要减去键盘高度
    private fun possiblyResizeChildOfContent() {
        //1､获取当前界面可用高度，键盘弹起后，当前界面可用布局会减少键盘的高度
        val usableHeightNow = computeUsableHeight()
        //2､如果当前可用高度和原始值不一样
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = contentHeight
            }
            //7､ 重绘Activity的xml布局
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom
    }

}