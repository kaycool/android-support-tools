package com.sirit.android.support.lib.widget.recycle

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView

/**
 * @author kai.w
 * @des  $des
 */
fun androidx.recyclerview.widget.RecyclerView.setSpace(spaceHorizontal: Int, spaceVertical: Int, color: Int = Color.TRANSPARENT) {
    addItemDecoration(SpaceDecoration(spaceHorizontal, spaceVertical, color))
}