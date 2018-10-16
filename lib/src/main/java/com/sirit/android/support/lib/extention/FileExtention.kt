package com.sirit.android.support.lib.extention

import android.graphics.Bitmap
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


fun File.compress(MAXSIZE: Int = 3000 * 3000, outImagePath: String): String {

    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true

    BitmapFactory.decodeFile(this.absolutePath, options)

    var srcWidth = options.outWidth.toFloat()
    var srcHeight = options.outHeight.toFloat()
    val digree = this.readPicDegree()

    var sample = 1
    while (srcWidth * srcHeight > MAXSIZE) {
        sample *= 2
        srcWidth /= 2
        srcHeight /= 2
    }
    options.inSampleSize = sample

    //todo 根据bitmap占用的内存 查看当前可用内存是否可以加载该 bitmap
//    BitmapFactory.decodeFile(this.absolutePath, options)
//    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
//    val freeMemory = Runtime.getRuntime().freeMemory()


    options.inJustDecodeBounds = false

    if (options.inSampleSize == 1) {
        val ori = File(this.absolutePath)
        val target = File(outImagePath)
        try {
            ori.copyTo(target)
        } catch (e: Exception) {
        }
        return outImagePath
    }


    var scaledBitmap: Bitmap? = null
    try {
        scaledBitmap = BitmapFactory.decodeFile(this.absolutePath, options)
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
    }
    if (scaledBitmap == null) {
        //todo 图片oom 问题导致的 上传不了
        return this.absolutePath
    }
    scaledBitmap.rotateBitmap(digree).saveToSdcard(filePath = outImagePath)
    return outImagePath
}