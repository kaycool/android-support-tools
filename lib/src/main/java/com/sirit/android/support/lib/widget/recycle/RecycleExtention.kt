package com.sirit.android.support.lib.widget.recycle

import android.graphics.Color
import android.support.v7.widget.RecyclerView

/**
 * @author kai.w
 * @des  $des
 */
fun RecyclerView.setSpace(spaceHorizontal: Int, spaceVertical: Int, color: Int = Color.TRANSPARENT) {
    addItemDecoration(SpaceDecoration(spaceHorizontal, spaceVertical, color))
}