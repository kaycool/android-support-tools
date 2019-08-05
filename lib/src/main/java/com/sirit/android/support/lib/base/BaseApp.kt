package com.sirit.android.support.lib.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.sirit.android.support.lib.network.RetrofitUtils
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

/**
 * @author kai.w
 * @des  $des
 */
open class BaseApp : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        /* bindings */
        import(androidXModule(this@BaseApp))
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val k = kodein
        println("BaseApp$k")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    companion object {
        lateinit var instance: BaseApp
    }
}