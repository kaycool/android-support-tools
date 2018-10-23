package com.sirit.android.support.lib.extention

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
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
import android.support.v4.content.FileProvider
import com.sirit.android.support.lib.BuildConfig
import com.sirit.android.support.lib.R
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


/**======================================获取系统文件分享到app的文件路径============================================**/

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is Google Photos.
 */
fun isGooglePhotosUri(uri: Uri): Boolean {
    return uri.authority == "com.google.android.apps.photos.content"
}

fun isNewGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.contentprovider" == uri.authority
}

fun Context.getSystemFilePathForUri(uri: Uri): String {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            && DocumentsContract.isDocumentUri(this, uri) -> {
            when {
                isExternalStorageDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    return if ("primary".equals(type, ignoreCase = true)) {
                        Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    } else {
                        ""
                    }
                }

                isDownloadsDocument(uri) -> {
                    val id = DocumentsContract.getDocumentId(uri)
                    if (!id.isNullOrEmpty() && id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "")
                    }
                    return try {
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), id.toLong())
                        getDataColumn(this, contentUri, null, null)
                    } catch (e: NumberFormatException) {
                        ""
                    }
                }

                isMediaDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    val contentUri: Uri? = when (type) {
                        "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        else -> null
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(this, contentUri, selection, selectionArgs)
                }
                else -> ""
            }
        }

        "content".equals(uri.scheme, ignoreCase = true) -> {
            return when {
                isGooglePhotosUri(uri) -> uri.lastPathSegment ?: ""
                isNewGooglePhotosUri(uri) -> getPathFromInputStreamUri(this, uri) ?: ""
                else -> getDataColumn(this, uri, null, null)
            }
        }

        "file".equals(uri.scheme, ignoreCase = true) -> {
            return uri.path ?: ""
        }

        else -> ""
    }
}


/**
 * Get the value of the data column for this Uri. This is useful for
 * MediaStore Uris, and other file-based ContentProviders.
 *
 * @param uri           The Uri to query.
 * @param selection     (Optional) Filter used in the query.
 * @param selectionArgs (Optional) Selection arguments used in the query.
 * @return The value of the _data column, which is typically a file path.
 */
fun getDataColumn(ctx: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String {
    var cursor: Cursor? = null
    val column = MediaStore.MediaColumns.DATA
    val projection = arrayOf(column)

    var filePath = ""

    try {
        cursor = ctx.contentResolver.query(uri, projection, selection, selectionArgs, null)
        cursor?.let {
            it.moveToFirst()
            val index = it.getColumnIndexOrThrow(column)
            filePath = it.getString(index)
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    } finally {
        cursor?.close()
    }

    return filePath
}

private fun getPathFromInputStreamUri(ctx: Context, uri: Uri): String? {
    var inputStream: InputStream? = null
    var outputStream: FileOutputStream? = null
    var filePath: String? = null

    if (uri.authority != null) {
        try {
            val outputFile = File(ctx.externalImgDir, "${UUID.randomUUID()}.jpg")
            inputStream = ctx.contentResolver.openInputStream(uri)

            outputStream = FileOutputStream(outputFile)

            val b = ByteArray(1024)
            var n = 0
            while (inputStream.read(b).apply { n = this } != -1) {
                outputStream.write(b, 0, n)
            }
            filePath = outputFile.path

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    return filePath
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