package com.sirit.android.support.media.demo

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.sirit.android.support.lib.compat.StatusBarCompat
import com.sirit.android.support.lib.extention.color
import com.sirit.android.support.lib.extention.dp2px
import com.sirit.android.support.lib.extention.openSystemCamera
import com.sirit.android.support.lib.extention.replaceToolbarLayout
import com.sirit.android.support.lib.media.MediaHelp
import com.sirit.android.support.lib.widget.help.ViewTitleHelp
import com.sirit.android.support.media.R
import kotlinx.android.synthetic.main.activity_media.*
import java.util.*

class MediaActivity : AppCompatActivity() {
    private val mPicture by lazy { findViewById<Button>(R.id.gallery) }
    private val mCapture by lazy { findViewById<Button>(R.id.capture) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        StatusBarCompat.compat(this)

        replaceToolbarLayout(toolbar = toolbar
            , viewTitleHelp = ViewTitleHelp.Companion.ViewTitleBuilder()
            .setTitleLeftData(ViewTitleHelp.Companion.TitleData(
                titleIcon = ViewTitleHelp.Companion.TitleIcon(
                    android.R.drawable.arrow_up_float,
                    dp2px(15f),
                    dp2px(15f),
                    Rect())))
            .setTitleCenterData(ViewTitleHelp.Companion.TitleData(
                titleText = ViewTitleHelp.Companion.TitleText(
                    "我是测试", dp2px(20f)
                    , color(R.color.support_colorAccent)
                    , Rect())))
            .build())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE
                , android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0x1111)
        }

        mPicture.setOnClickListener {
            MediaHelp.Companion.Build(this)
                .setSpanCount(2)
                .build()
                .showImages()
        }
        mCapture.setOnClickListener {
            openSystemCamera(Params.REQUEST_SYSTEM_CAPTURE_CODE
                , "${Environment.getExternalStorageDirectory()}/$packageName/capture/${UUID.randomUUID()}.jpg")
        }
    }
}
