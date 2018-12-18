package com.sirit.android.support.lib

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * @author kai.w
 * @des  $des
 */
open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(ForegroundCallback)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    companion object {
        lateinit var instance: BaseApp
    }
}