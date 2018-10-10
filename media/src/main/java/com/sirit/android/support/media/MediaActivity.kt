package com.sirit.android.support.media

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import com.sirit.android.support.extention.openSystemCamera
import com.sirit.android.support.lib.compat.StatusBarCompat
import java.util.*

class MediaActivity : AppCompatActivity() {
    private val mPicture by lazy { findViewById<Button>(R.id.gallery) }
    private val mCapture by lazy { findViewById<Button>(R.id.capture) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        StatusBarCompat.compat(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE
            ,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),0x1111)
        }

        mPicture.setOnClickListener {
            MediaHelp.Builder(this).startGallery()
        }
        mCapture.setOnClickListener {
            openSystemCamera(Params.REQUEST_SYSTEM_CAPTURE_CODE
                , "${Environment.getExternalStorageDirectory()}/$packageName/capture/${UUID.randomUUID()}.jpg")
        }
    }
}
