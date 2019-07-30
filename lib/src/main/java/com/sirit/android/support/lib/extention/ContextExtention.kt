package com.sirit.android.support.lib.extention

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.util.Log
import android.content.Intent
import android.database.Cursor
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Contacts
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.content.FileProvider
import android.widget.Toast
import com.sirit.android.support.lib.BuildConfig
import com.sirit.android.support.lib.R
import com.sirit.android.support.lib.constant.PROJECT_CONFIG_FILE
import java.io.*
import java.util.*


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

fun Context.color(color: Int): Int = ContextCompat.getColor(this, color)

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

fun Context.createShapeDrawable(radiusPx: Float, solidColor: Int): Drawable = GradientDrawable().apply {
    this.cornerRadius = radiusPx
    this.setColor(solidColor)
}

fun Context.createShapeDrawable(radiiPx: FloatArray, solidColor: Int): Drawable = GradientDrawable().apply {
    this.cornerRadii = radiiPx
    this.setColor(solidColor)
}

fun Context.createShapeDrawable(colors: IntArray
                                , radiiPx: FloatArray
                                , orientation: GradientDrawable.Orientation): Drawable = GradientDrawable(orientation, colors).apply { this.cornerRadii = radiiPx }

fun Context.createShapeDrawable(radiusPx: Float
                                , strokeWidth: Int
                                , strokeColor: Int): Drawable = GradientDrawable().apply {
    this.cornerRadius = radiusPx
    this.setStroke(strokeWidth, strokeColor)
}

fun Context.createShapeDrawable(radiusPx: Float
                                , strokeWidth: Int
                                , strokeColor: Int
                                , solidColor: Int): Drawable = GradientDrawable().apply {
    this.cornerRadius = radiusPx
    this.setColor(solidColor)
    this.setStroke(strokeWidth, strokeColor)
}

fun Context.getUri(file: File): Uri? {
    try {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            FileProvider.getUriForFile(this, this.packageName, file)
        } else {
            Uri.fromFile(file)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

val Context.saveDir: File
    get() {
        val dir = File(Environment.getExternalStorageDirectory().absolutePath + "/${string(R.string.app_name)}")
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }

val Context.externalImgDir: String
    get() {
        val path = externalCacheDir.absolutePath + "/img"
        var file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return path
    }
val Context.messageCacheDir: String
    get() {
        return externalCacheDir.absolutePath + "/message_cache"
    }

fun Context.showToast(text: String) {
    showToast(text, 81, 0)
}

fun Context.showToast(rsid: Int) {
    showToast(rsid, 81, 0)
}

fun Context.showToast(rsid: Int, gravity: Int, duration: Int) {
    showToast(this.string(rsid), gravity, 0, 0, duration)
}

fun Context.showToast(text: String, gravity: Int, duration: Int) {
    showToast(text, gravity, 0, 0, duration)
}

fun Context.showToast(text: String, gravity: Int, xOffset: Int, yOffset: Int, duration: Int) {
    val toast = Toast.makeText(this, text, duration)
    toast.show()
}

/**============================================SharedPreferences==============================================**/
fun Context.putString(key: String, value: String) {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}

fun Context.putBoolean(key: String, value: Boolean?) {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(key, value!!)
    editor.apply()
}

fun Context.putInt(key: String, value: Int?) {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt(key, value!!)
    editor.apply()
}

fun Context.putLong(key: String, value: Long?) {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putLong(key, value!!)
    editor.apply()
}

fun Context.putFloat(key: String, value: Float?) {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putFloat(key, value!!)
    editor.apply()
}

fun Context.getString(key: String, defaultValue: String): String {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, defaultValue) ?: ""
}

fun Context.getBoolean(key: String, defaultValue: Boolean?): Boolean {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, defaultValue!!)
}

fun Context.getInteger(key: String, defaultValue: Int?): Int? {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(key, defaultValue!!)
}

fun Context.getLong(key: String, defaultValue: Long?): Long? {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    return sharedPreferences.getLong(key, defaultValue!!)
}

fun Context.getFloat(key: String, defaultValue: Float?): Float? {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    return sharedPreferences.getFloat(key, defaultValue!!)
}

fun Context.putAll(map: Map<String, *>) {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    for (key in map.keys) {
        val value = map[key]
        when (value) {
            is String -> editor.putString(key, value)
            is Long -> editor.putLong(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            else -> editor.putString(key, value.toString())
        }
    }
    editor.apply()
}

fun Context.clearContextSP() {
    val sharedPreferences = getSharedPreferences(PROJECT_CONFIG_FILE, Context.MODE_PRIVATE)
    sharedPreferences.edit().clear().apply()
}


/**============================================系统属性==============================================**/
fun Context.scanMediaSdcard(file: File) {
    MediaScannerConnection.scanFile(this, arrayOf<String>(file.absolutePath), null) { path, uri ->
        Log.v("MediaScanWork", "file $path was scanned seccessfully: $uri")
    }
}

fun Context.openSystemCamera(requestCode: Int, outputFilePath: String? = null) {
    val intent = Intent()
    // 指定开启系统相机的Action
    intent.action = MediaStore.ACTION_IMAGE_CAPTURE
    val dir = File(outputFilePath)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", File(outputFilePath))
    } else {
        Uri.fromFile(File(outputFilePath))
    }
    // 设置系统相机拍摄照片完成后图片文件的存放地址
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
    (this as? Activity?)?.startActivityForResult(intent, requestCode)
}

fun Context.openSystemGallery(requestCode: Int) {
    val intent = Intent(Intent.ACTION_PICK, null)
    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
    intent.action = Intent.ACTION_GET_CONTENT
    (this as? Activity?)?.startActivityForResult(intent, requestCode)
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

//系统联系人界面
fun Context.openSystemConstactActivity() {
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.data = Contacts.People.CONTENT_URI
    startActivity(intent)
}

//系统短信界面
fun Context.openSystemMsgActivity() {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.type = "vnd.android-dir/mms-sms"
//intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码
    startActivity(intent)
}