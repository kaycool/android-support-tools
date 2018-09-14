package com.sirit.android.support.extention

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import java.io.File
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.ContextCompat.startActivity
import android.os.Environment.getExternalStorageDirectory
import android.provider.Contacts
import android.support.v4.content.ContextCompat.startActivity
import java.util.ArrayList
import android.support.v4.content.ContextCompat.startActivity


/**
 * @author kai.w
 *         Context 上下文的扩展类，持续完善
 */

val Context.density: Int
    get() {
        val density = resources.displayMetrics.density
        return (density + 0.5f).toInt()
    }

val Context.widthPixels: Int
    get() {
        val displayMetrics = resources.displayMetrics
        val cf = resources.configuration
        val ori = cf.orientation
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            return displayMetrics.heightPixels
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            return displayMetrics.widthPixels
        }
        return 0
    }

val Context.heightPixels: Int
    get() {
        val displayMetrics = resources.displayMetrics
        val cf = resources.configuration
        val ori = cf.orientation
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            return displayMetrics.widthPixels
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            return displayMetrics.heightPixels
        }
        return 0
    }

fun Context.color(color: Int) = ContextCompat.getColor(this, color)

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.dimen(resId: Int): Int = resources.getDimensionPixelSize(resId)

fun Context.string(id: Int): String = resources.getString(id)

fun Context.svgDrawable(id: Int): Drawable? {
    try {
        return VectorDrawableCompat.create(resources, id, null)
            ?: mipmapDrawable(id)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return mipmapDrawable(id)
}

fun Context.mipmapDrawable(id: Int): Drawable? = ResourcesCompat.getDrawable(resources, id, null)


/**============================================系统属性==============================================**/
fun Context.scanMediaSdcard(file: File) {
    MediaScannerConnection.scanFile(this, arrayOf<String>(file.absolutePath), null) { path, uri ->
        Log.v("MediaScanWork", "file $path was scanned seccessfully: $uri")
    }
}


fun Context.systemShareText(shareText: String, shareTitle: String = string(R.string.system_share_title_default)) {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
    shareIntent.type = "text/plain"

    //设置分享列表的标题，并且每次都显示分享列表
    startActivity(Intent.createChooser(shareIntent, shareTitle))
}

fun Context.systemShareImg(imgPath: String, shareTitle: String = string(R.string.system_share_title_default)) {
    //由文件得到uri
    val imageUri = Uri.fromFile(File(imgPath))
    Log.d("system-share", "uri:$imageUri")

    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    shareIntent.type = "image/*"
    startActivity(Intent.createChooser(shareIntent, shareTitle))
}

fun Context.systemMultiShare(imgPathList: MutableList<String>, shareTitle: String = string(R.string.system_share_title_default)) {
    val uriList = ArrayList<Uri>()
    imgPathList.forEach {
        uriList.add(Uri.fromFile(File(it)))
    }
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND_MULTIPLE
    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
    shareIntent.type = "image/*"
    startActivity(Intent.createChooser(shareIntent, shareTitle))
}

fun Context.openSystemCallActivity() {
    val intent = Intent()
    intent.action = Intent.ACTION_CALL_BUTTON
    startActivity(intent)
}

fun Context.openSystemDialActivity(telPhone: String) {
    val uri = Uri.parse("tel:$telPhone")
    val intent = Intent(Intent.ACTION_DIAL, uri)
    startActivity(intent)
}

fun Context.openSystemPhoneApp() {
    val intent = Intent("android.intent.action.DIAL")
    intent.setClassName("com.android.contacts", "com.android.contacts.DialtactsActivity")
    startActivity(intent)
}

fun Context.openSystemConstactActivity() {
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.data = Contacts.People.CONTENT_URI
    startActivity(intent)
}