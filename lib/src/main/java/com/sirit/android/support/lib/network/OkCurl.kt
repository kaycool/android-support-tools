package com.sirit.android.support.lib.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author kai.w
 * @des  $des
 */
class OkCurl(private val tag: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d(tag, OkCurlWorker(request).work())
        return chain.proceed(request)
    }

}