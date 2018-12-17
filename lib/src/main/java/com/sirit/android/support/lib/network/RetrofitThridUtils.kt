package com.sirit.android.support.lib.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitThridUtils {

    private val TAG = "Retrofit"

    private val DEFAULT_TIMEOUT = 10
    private lateinit var mRetrofit: Retrofit
    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d(TAG, message) }).setLevel(HttpLoggingInterceptor.Level.BODY)

    fun getRetrofit(url: String): Retrofit {
        if (this::mRetrofit.isInitialized || url != mRetrofit.baseUrl().toString()) {
            val builder = OkHttpClient.Builder()
                    .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor) //日志处理
                    .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            val client = builder.build()
            mRetrofit = Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(url)
                    .build()
        }
        return mRetrofit
    }

}
