package com.sirit.android.support.lib.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * @author kai.w
 * @des  $des
 */
open class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}