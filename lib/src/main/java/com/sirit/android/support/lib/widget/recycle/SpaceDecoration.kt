package com.sirit.android.support.lib.widget.recycle

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager


/**
 * @author kai.w
 * @des  $des
 */
class SpaceDecoration(private val mSpaceHorizontal: Int, private val mSpaceVertical: Int, mColor: Int = Color.TRANSPARENT) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
    private val mPaint by lazy { Paint() }


    init {
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        mPaint.color = mColor
    }

    override fun onDrawOver(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val layoutManager = parent.layoutManager
        when (layoutManager) {
            is androidx.recyclerview.widget.GridLayoutManager -> drawGridItemSpace(c, parent, layoutManager)
            is androidx.recyclerview.widget.LinearLayoutManager -> drawLinearItemSpace(c, parent, layoutManager)
            is androidx.recyclerview.widget.StaggeredGridLayoutManager -> drawStaggeredItemSpace(c, parent, layoutManager)
        }
    }

    override fun onDraw(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.onDraw(c, parent, state)
    }


    private fun drawLinearItemSpace(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, linearManager: androidx.recyclerview.widget.LinearLayoutManager) {

        if (linearManager.orientation == androidx.recyclerview.widget.LinearLayoutManager.VERTICAL) {
            for (viewPosition in 0 until linearManager.itemCount) {
                val childView = parent.getChildAt(viewPosition) ?: continue

                val params = childView.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams

                val left: Int
                var top: Int
                val right: Int
                var bottom: Int
                left = childView.left - params.leftMargin
                right = childView.right + params.rightMargin
                top = childView.bottom + params.bottomMargin
                bottom = top + mSpaceVertical
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
                if (viewPosition == 0) {
                    top = childView.top - params.topMargin - mSpaceVertical
                    bottom = top + mSpaceVertical
                    c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
                }
            }
        } else {
            for (viewPosition in 0 until linearManager.itemCount) {
                val childView = parent.getChildAt(viewPosition) ?: continue

                val params = childView.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams

                var left: Int
                var top: Int
                var right: Int
                var bottom: Int


                left = childView.right + params.rightMargin
                top = childView.top - params.topMargin
                right = left + mSpaceHorizontal
                bottom = childView.bottom + params.bottomMargin
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

                if (viewPosition == 0) {
                    left = childView.left - params.leftMargin - mSpaceHorizontal
                    top = childView.top - params.topMargin
                    right = left + mSpaceHorizontal
                    bottom = childView.bottom + params.bottomMargin
                    c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
                }
            }
        }
    }


    private fun drawGridItemSpace(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, gridManager: androidx.recyclerview.widget.GridLayoutManager) {
        for (viewPosition in 0 until gridManager.itemCount) {
            val childView = parent.getChildAt(viewPosition) ?: continue

            val params = childView.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams

            val spanCount = gridManager.spanCount
            val index = viewPosition % spanCount

            var left: Int
            var top: Int
            var right: Int
            var bottom: Int

            left = childView.left - params.leftMargin
            right = childView.right + params.rightMargin + mSpaceHorizontal
            if (viewPosition < spanCount) {//第一行
                top = childView.top - params.topMargin - mSpaceVertical
                bottom = top + mSpaceVertical
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

                top = childView.bottom + params.bottomMargin
                bottom = top + mSpaceVertical
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            } else {
                top = childView.bottom + params.bottomMargin
                bottom = top + mSpaceVertical
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }


            if (index == 0) {//第一列
                top = childView.top - params.topMargin
                bottom = childView.bottom + params.bottomMargin + mSpaceVertical
                left = childView.left - params.leftMargin - mSpaceHorizontal
                right = left + mSpaceHorizontal
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

                top = childView.top - params.topMargin
                bottom = childView.bottom + params.bottomMargin
                left = childView.right + params.rightMargin
                right = left + mSpaceHorizontal
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

                if (viewPosition == 0) {
                    top = childView.top - params.topMargin - mSpaceVertical
                    bottom = top + mSpaceVertical
                    left = childView.left - params.leftMargin - mSpaceHorizontal
                    right = left + mSpaceHorizontal
                    c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
                }

            } else {
                top = childView.top - params.topMargin
                bottom = childView.bottom + params.bottomMargin
                left = childView.right + params.rightMargin
                right = left + mSpaceHorizontal
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }
        }
    }

    private fun drawStaggeredItemSpace(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView, gridManager: androidx.recyclerview.widget.StaggeredGridLayoutManager) {

    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager = parent.layoutManager
        when (layoutManager) {
            is androidx.recyclerview.widget.GridLayoutManager -> layoutGridItem(outRect, view, parent, layoutManager)
            is androidx.recyclerview.widget.LinearLayoutManager -> layoutLinearItem(outRect, view, parent, layoutManager)
            is androidx.recyclerview.widget.StaggeredGridLayoutManager -> layoutStaggeredItem(outRect, view, parent, layoutManager)
        }
    }

    private fun layoutLinearItem(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, linearManager: androidx.recyclerview.widget.LinearLayoutManager) {
        val viewPosition = parent.getChildAdapterPosition(view)

        if (linearManager.orientation == androidx.recyclerview.widget.LinearLayoutManager.VERTICAL) {
            outRect.left = mSpaceHorizontal
            outRect.right = mSpaceHorizontal
            when (viewPosition) {
                0 -> {
                    outRect.top = mSpaceVertical
                    outRect.bottom = mSpaceVertical / 2
                }
                linearManager.itemCount - 1 -> {
                    outRect.top = mSpaceVertical / 2
                    outRect.bottom = mSpaceVertical
                }
                else -> {
                    outRect.top = mSpaceVertical / 2
                    outRect.bottom = mSpaceVertical / 2
                }
            }
        } else {
            outRect.top = mSpaceVertical
            outRect.bottom = mSpaceVertical

            when (viewPosition) {
                0 -> {
                    outRect.left = mSpaceHorizontal
                    outRect.right = mSpaceHorizontal / 2
                }
                linearManager.itemCount - 1 -> {
                    outRect.left = mSpaceHorizontal / 2
                    outRect.right = mSpaceHorizontal
                }
                else -> {
                    outRect.left = mSpaceHorizontal / 2
                    outRect.right = mSpaceHorizontal / 2
                }
            }
        }
    }


    private fun layoutGridItem(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, gridManager: androidx.recyclerview.widget.GridLayoutManager) {
        val viewPosition = parent.getChildAdapterPosition(view)
        val spanCount = gridManager.spanCount
        val index = viewPosition % spanCount


        when {
            viewPosition < spanCount -> {//第一行
                outRect.top = mSpaceVertical
                outRect.bottom = mSpaceVertical / 2
            }
            viewPosition / spanCount == (gridManager.itemCount - 1) / spanCount -> {//多宫格如何判断最后一行？
                outRect.top = mSpaceVertical / 2
                outRect.bottom = mSpaceVertical
            }
            else -> {
                outRect.top = mSpaceVertical / 2
                outRect.bottom = mSpaceVertical / 2
            }
        }


        when (index) {
            0 -> {//第一列
                outRect.left = mSpaceHorizontal
                outRect.right = mSpaceHorizontal / 2

            }
            spanCount - 1 -> {//最后一列
                outRect.left = mSpaceHorizontal / 2
                outRect.right = mSpaceHorizontal

            }
            else -> {
                outRect.left = mSpaceHorizontal / 2
                outRect.right = mSpaceHorizontal / 2
            }
        }
    }

    private fun layoutStaggeredItem(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, staggeredManager: androidx.recyclerview.widget.StaggeredGridLayoutManager) {

    }
}