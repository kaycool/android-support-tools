package com.sirit.android.support.lib.extention

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author kai.w
 * @des  $des
 */

fun Bitmap.saveToSdcard(filePath: String, needRecycle: Boolean = true, callBack: (File) -> Unit = {}, context: () -> Context? = { null }) {
    Thread {
        val file = File(filePath)
        var bos: BufferedOutputStream? = null
        try {
            bos = BufferedOutputStream(FileOutputStream(file))
            this.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos?.close()
            } catch (e: Exception) {
            }
            if (needRecycle) {
                this.recycle()
            }
            Handler(Looper.getMainLooper()).post {
                context()?.scanMediaSdcard(file)
                callBack(file)
            }
        }
    }.start()
}

fun Bitmap.saveToSdcard(saveImgPath: String, maxFileLength: Int = 200 * 1024, needRecycle: Boolean = true, callBack: (File) -> Unit = {}, context: () -> Context? = { null }) {
    Thread {
        val file = File(saveImgPath)
        var bos: BufferedOutputStream? = null
        try {
            var tempQuality = 100
            do {
                if (tempQuality < 50) {
                    break
                }
                bos = BufferedOutputStream(FileOutputStream(file))
                this.compress(Bitmap.CompressFormat.JPEG, tempQuality, bos)
                bos.flush()
                tempQuality -= 10
            } while (file.length() > maxFileLength)

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos?.close()
            } catch (e: Exception) {
            }
            if (needRecycle) {
                this.recycle()
            }
            Handler(Looper.getMainLooper()).post {
                context()?.scanMediaSdcard(file)
                callBack(file)
            }
        }
    }.start()
}

fun Bitmap.rotateBitmap(degree: Int): Bitmap = if (degree != 0) {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotateBitmap = Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
    if (!this.isRecycled) {
        this.recycle()
    }
    rotateBitmap
} else {
    this
}

fun Bitmap.createCircleBitmap(color: Int): Bitmap {
    val size = if (this.width < this.height) {
        this.height
    } else {
        this.width
    }

    val outputBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(outputBitmap)

    val paint = Paint()
    val rect = Rect(0, 0, size, size)
    val roundPx = size.toFloat() / 2
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = Color.parseColor("#FFFFFF")
    canvas.drawCircle(roundPx, roundPx, roundPx, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    return outputBitmap
}