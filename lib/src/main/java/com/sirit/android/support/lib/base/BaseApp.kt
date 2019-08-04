package com.sirit.android.support.lib.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * @author kai.w
 * @des  $des
 */
open class BaseApp : Application(), KodeinAware {

    private val appModule = Kodein.Module("APP") {
        bind<Application>() with singleton { BaseApp() }
    }

    override val kodein by Kodein.lazy {
        /* bindings */
        import(appModule)
        import(androidXModule(this@BaseApp))
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    companion object {
        lateinit var instance: BaseApp
    }
}