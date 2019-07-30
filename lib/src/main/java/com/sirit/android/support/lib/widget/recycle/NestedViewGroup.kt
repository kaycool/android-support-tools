package com.sirit.android.support.lib.widget.recycle

import android.content.Context
import android.graphics.Canvas
import androidx.core.view.NestedScrollingParent
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * @author kai.w
 * @des  $des
 */
class NestedViewGroup : ViewGroup, NestedScrollingParent {

    private var mDefaultHeaderView: NestedDefaultHeaderView? = null
    private var mDefaultFooterView: NestedDefaultFooterView? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        addHeaderAndFooter()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }


    fun addHeaderAndFooter() {
        
    }


    override fun onNestedScrollAccepted(p0: View, p1: View, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartNestedScroll(p0: View, p1: View, p2: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNestedFling(p0: View, p1: Float, p2: Float, p3: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNestedScrollAxes(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNestedPreScroll(p0: View, p1: Int, p2: Int, p3: IntArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNestedScroll(p0: View, p1: Int, p2: Int, p3: Int, p4: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNestedPreFling(p0: View, p1: Float, p2: Float): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopNestedScroll(p0: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}