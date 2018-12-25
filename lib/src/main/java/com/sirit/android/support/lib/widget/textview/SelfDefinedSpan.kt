package com.sirit.android.support.lib.widget.textview

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.style.ImageSpan
import android.text.style.ReplacementSpan

/**
 * @author kai.w
 * @des  $des
 */

class ImageCenterSpan(drawable: Drawable, verticalAlignment: Int) : ImageSpan(drawable, verticalAlignment) {

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val drawable = drawable
        val fontMetricsInt = paint?.fontMetricsInt
        val transY = (y + fontMetricsInt!!.descent + y + fontMetricsInt.ascent) / 2 - drawable.bounds.bottom / 2
        canvas?.save()
        canvas?.translate(x, transY.toFloat())
        drawable.draw(canvas)
        canvas?.restore()
    }
}

class RoundRectArrowSpan(val arrowDrawable: Drawable? = null, val bgRadius: Float = 0f, val backgroundColor: Int, val foregroudColor: Int, val textSize: Int, val drawPadding: Int = 0, val padding: Rect) : ReplacementSpan() {
    private val mRect = Rect()
    private val mIndicatorDrawable = GradientDrawable()

    override fun getSize(paint: Paint?, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return textSize
    }

    override fun draw(canvas: Canvas?, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint?) {
        paint?.also {
            it.textSize = textSize.toFloat()
            // Background
            mRect.set(x.toInt(), top - padding.top, x.toInt() + it.measureText(text, start, end).toInt() + padding.left + padding.right, bottom + padding.bottom)
//        it.color = backgroundColor
//        canvas.drawRect(mRect, it)
            mIndicatorDrawable.setColor(backgroundColor)
            mIndicatorDrawable.bounds = mRect
            mIndicatorDrawable.cornerRadius = bgRadius
            mIndicatorDrawable.draw(canvas)

            // Text
            it.color = foregroudColor
            it.isFakeBoldText = true
            val xPos = x + padding.left
            canvas?.drawText(text, start, end, xPos, y.toFloat(), it)

            //drawable
            arrowDrawable?.also {
                val fontMetricsInt = paint.fontMetricsInt
                val transY = (y + fontMetricsInt!!.descent + y + fontMetricsInt.ascent) / 2 - it.bounds.bottom / 2
                canvas?.save()
                canvas?.translate(xPos + paint.measureText(text, start, end).toInt() + drawPadding, transY.toFloat())
                it.draw(canvas)
                canvas?.restore()
            }

        }
    }

}