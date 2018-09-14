package com.sirit.android.support.extention

import android.graphics.BitmapFactory
import android.media.ExifInterface
import java.io.File

/**
 * @author kai.w
 * @des  $des
 */

fun File.isGif(): Boolean {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(absolutePath, options)
    val type = options.outMimeType
    return type == "image/gif"
}

fun File.deleteFile() {
    if (this.isDirectory) {
        val files = this.listFiles()
        for (i in files.indices) {
            val f = files[i]
            f.deleteFile()
        }
    } else if (this.exists()) {
        this.delete()
    }
}


fun File.compressImgToSdcard(outImagePath: String, maxSize: Int = 3000 * 3000) {
    Thread {
        val srcImagePath = this.absolutePath
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(srcImagePath, options)

        var srcWidth = options.outWidth
        var srcHeight = options.outHeight
        var digree = readPicDegree()
        var sample = 1
        while (srcWidth * srcHeight > maxSize) {
            sample *= 2
            srcWidth /= 2
            srcHeight /= 2
        }
        options.inSampleSize = sample
        options.inJustDecodeBounds = false
        if (options.inSampleSize == 1) {
            val source = File(srcImagePath)
            val target = File(outImagePath)
            try {
                source.copyTo(target)
            } catch (e: Exception) {
            }
            return@Thread
        }
    }.start()
}


fun File.readPicDegree(): Int {
    var degree = 0

    try {
        val exif = ExifInterface(this.absolutePath)
        val ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        degree = when (ori) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    } catch (e: Exception) {

    }

    return degree
}