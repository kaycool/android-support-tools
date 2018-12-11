package com.sirit.android.support.lib.widget.recycle

import android.content.Context
import android.support.v4.view.NestedScrollingChild
import android.util.AttributeSet
import android.widget.FrameLayout
import com.sirit.android.support.lib.widget.recycle.listener.NestedHeaderListener

/**
 * @author kai.w
 * @des  $des
 */
class NestedDefaultHeaderView : FrameLayout, NestedScrollingChild , NestedHeaderListener {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchNestedScroll(p0: Int, p1: Int, p2: Int, p3: Int, p4: IntArray?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isNestedScrollingEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispatchNestedPreScroll(p0: Int, p1: Int, p2: IntArray?, p3: IntArray?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopNestedScroll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasNestedScrollingParent(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispatchNestedPreFling(p0: Float, p1: Float): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setNestedScrollingEnabled(p0: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispatchNestedFling(p0: Float, p1: Float, p2: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startNestedScroll(p0: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun isUp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isDown() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}