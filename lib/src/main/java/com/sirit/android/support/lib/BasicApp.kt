package com.sirit.android.support.lib

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * @author kai.w
 * @des  $des
 */
class BasicApp : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ForegroundCallback)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}