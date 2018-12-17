package com.sirit.android.support.lib.utils

import android.util.Log
import com.sirit.android.support.lib.BuildConfig

/**
 * @author kai.w
 * @des  $des
 */

private val TAG = "SIR_IT"

fun logd(tag: String = TAG, msg: String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, msg)
    }
}

fun loge(tag: String = TAG, msg: String) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, msg)
    }
}