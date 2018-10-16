package com.sirit.android.support.lib.widget.flow

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @author kai.w
 * @des  $des
 */
class FlowLayout : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}