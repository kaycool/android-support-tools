package com.sirit.android.support.lib.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.tencent.mmkv.MMKV
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import java.io.File

/**
 * @author kai.w
 * @des  $des
 */
open class BaseApp : Application(), KodeinAware {
    private val thirdModule = Kodein.Module("api") {
        bind<MMKV>(tag = "MMKV_DEFAULT") with singleton { MMKV.defaultMMKV() }
        constant(tag = "mmkv_dir") with "$packageName${File.separator}mmkv_dir"
    }
    override val kodein by Kodein.lazy {
        /* bindings */
        import(androidXModule(this@BaseApp))
        import(thirdModule)
    }
    private val rootDir: String  by instance("mmkv_dir")

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(rootDir)
        val k = kodein
        println("BaseApp$k,rootDir=$rootDir")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    companion object {
        lateinit var instance: BaseApp
    }
}