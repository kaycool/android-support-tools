package com.sirit.android.support.lib.media

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author kai.w
 * @des  $des
 */
class GalleryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),0x111111)
        }
    }



}