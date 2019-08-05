package com.sirit.android.support

import com.sirit.android.support.lib.base.BaseApp
import com.sirit.android.support.lib.network.RetrofitUtils
import org.kodein.di.Kodein
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

/**
 * @author kai.w
 * @des  $des
 */
class App : BaseApp() {
    private val appModule = Kodein.Module("App") {
        //        bind<Application>() with singleton { BaseApp() }
        bind<Retrofit>() with singleton { RetrofitUtils.getRetrofit("http://www.baidu.com/") }
    }
    override val kodein by Kodein.lazy {
        /* bindings */
        extend(super.kodein)
        import(appModule)
    }

    override fun onCreate() {
        super.onCreate()

//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this)
    }
}