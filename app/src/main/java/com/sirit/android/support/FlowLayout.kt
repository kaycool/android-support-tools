package com.sirit.android.support

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.OverScroller

/**
 * @author kai.w
 *      ${DES}
 */
class FlowLayout : ViewGroup {
    val scroll = OverScroller(context)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setWillNotDraw(false)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)


        var currentWidth = 0
        var lineHeight = 0
        var totalHeight = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)
            val params = child.layoutParams
            measureChild(child, widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED))

            currentWidth += params.width

            if (currentWidth > width) {
                currentWidth = params.width
                totalHeight += params.height
            }
            lineHeight = Math.max(lineHeight, params.height)
        }

        setMeasuredDimension(width, totalHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentWidth = 0
        var lineHeight = 0
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0



        for (index in 0 until childCount) {
            val child = getChildAt(index)

            val layoutParams = child.layoutParams

            right = left + layoutParams.width
            bottom = top + layoutParams.height

            child.layout(left, top, right, bottom)
            left = right

            currentWidth += layoutParams.width

            if (currentWidth > measuredWidth) {
                left = 0
                top = lineHeight
                currentWidth = layoutParams.width
            }

            lineHeight = Math.max(lineHeight, layoutParams.height)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val recycleView = getChildAt(2)

       if(recycleView.canScrollVertically(-1)) {
           return true
       }

       if(scrollY==getScrollRange()) {

       }


        return true
    }

    private fun getScrollRange(): Int {
        val child = getChildAt(0)
        return child.height

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_MOVE) {

//            if (!canScrollVertically(-1)) {
//                dy = 0
//            }
//            scrollby(dx, dy)
        }

        return super.onTouchEvent(event)
    }


    override fun canScrollHorizontally(direction: Int): Boolean {
        return super.canScrollHorizontally(direction)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        if (direction < 0) {//scroll up
            return top < 0
        } else if (direction > 0) {//scroll down

        }
        return super.canScrollVertically(direction)
    }

}