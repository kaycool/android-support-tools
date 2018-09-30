package com.sirit.android.support.extention

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author kai.w
 * @des  $des
 */


fun currentTimeMillis() = System.currentTimeMillis()

fun Long.formatMd(): String {
    val monthFormat = SimpleDateFormat("M月dd日", Locale.CHINESE)
    return if (this@formatMd.toString().length == currentTimeMillis().toString().length) {
        monthFormat.format(Date(this@formatMd))
    } else {
        monthFormat.format(Date(this@formatMd * 1000))
    }
}


fun Long.formatYMd(): String {
    val yearFormat = SimpleDateFormat("yyyy年M月dd日", Locale.CHINESE)
    return if (this@formatYMd.toString().length == currentTimeMillis().toString().length) {
        yearFormat.format(Date(this@formatYMd))
    } else {
        yearFormat.format(Date(this@formatYMd * 1000))
    }
}

fun Long.formatYMd_2(): String {
    val yearFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return if (this@formatYMd_2.toString().length == currentTimeMillis().toString().length) {
        yearFormat.format(Date(this@formatYMd_2))
    } else {
        yearFormat.format(Date(this@formatYMd_2 * 1000))
    }
}

fun Long.formatYMd_3(): String {
    val yearFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return if (this@formatYMd_3.toString().length == currentTimeMillis().toString().length) {
        yearFormat.format(Date(this@formatYMd_3))
    } else {
        yearFormat.format(Date(this@formatYMd_3 * 1000))
    }
}

fun Long.formatYMdDot(): String {
    val yearFormat = SimpleDateFormat("yyyy.M.dd", Locale.getDefault())
    return if (this@formatYMdDot.toString().length == currentTimeMillis().toString().length) {
        yearFormat.format(Date(this@formatYMdDot))
    } else {
        yearFormat.format(Date(this@formatYMdDot * 1000))
    }
}