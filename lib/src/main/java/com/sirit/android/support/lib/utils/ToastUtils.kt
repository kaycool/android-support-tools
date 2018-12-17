package com.sirit.android.support.lib.utils

import android.content.Context
import android.widget.Toast

/**
 * @author kai.w
 * @des  $des
 */

fun showToast(context: Context, text: String) {
    showToast(context, text, 81, 0)
}

fun showToast(context: Context, rsid: Int) {
    showToast(context, rsid, 81, 0)
}

fun showToast(context: Context, rsid: Int, gravity: Int, duration: Int) {
    showToast(context, context.getString(rsid), gravity, 0, 0, duration)
}

fun showToast(context: Context, text: String, gravity: Int, duration: Int) {
    showToast(context, text, gravity, 0, 0, duration)
}

fun showToast(context: Context, text: String, gravity: Int, xOffset: Int, yOffset: Int, duration: Int) {
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}