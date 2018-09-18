package com.sirit.android.support

import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.sirit.android.support.lib.BasicApp

/**
 * @author kai.w
 * @des  $des
 */
class App : BasicApp() {

    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}