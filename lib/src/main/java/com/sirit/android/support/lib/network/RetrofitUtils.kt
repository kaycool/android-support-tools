package com.sirit.android.support.lib.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object RetrofitUtils {

    private val TAG = "Retrofit"

    private val DEFAULT_TIMEOUT = 10
    private lateinit var mRetrofit: Retrofit
    /**
     * 添加头部信息
     */
    private val interceptor = Interceptor { chain ->
        val request = chain.request()

        //            String token = UserDao.getToken();

        val newRequest = request.newBuilder()
            .addHeader("AC-X-TYPE", "1")
            //                    .addHeader("AC-X-TOKEN", token)
            //                    .addHeader("AC-X-VERSION", PackageUtil.getVersionName())
            .addHeader("Accept", "application/json")
            .removeHeader("User-Agent")
            //                    .addHeader("User-Agent", "azoyaclub_android_" + PackageUtil.getVersionName())
            .build()

        chain.proceed(newRequest)
    }
    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d(TAG, message) }).setLevel(HttpLoggingInterceptor.Level.BODY)
    var curlInterceptor = OkCurl("cURL String =")

    fun getRetrofit(baseUrl: String): Retrofit {
        if (!this::mRetrofit.isInitialized || baseUrl != mRetrofit.baseUrl().toString()) {
            val builder = OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//                    .addInterceptor(interceptor)//拦截器处理通用Header
                .addInterceptor(loggingInterceptor) //日志处理
                .addInterceptor(curlInterceptor) //curl
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)

            if (!baseUrl.isEmpty() && baseUrl.toLowerCase().contains("https:")) {
                try {
                    createSSLSocketFactory()?.also {
                        builder.sslSocketFactory(it)
                        builder.hostnameVerifier(TrustAllHostnameVerifier())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, e.message)
                }
            }

            val client = builder.build()

            mRetrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
        }
        return mRetrofit
    }

    fun getRetrofit(baseUrl: String, headerInterceptor: Interceptor): Retrofit {
        if (!this::mRetrofit.isInitialized || baseUrl != mRetrofit.baseUrl().toString()) {
            val builder = OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)//拦截器处理通用Header
                .addInterceptor(loggingInterceptor) //日志处理
                .addInterceptor(curlInterceptor) //curl
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)

            if (!baseUrl.isEmpty() && baseUrl.toLowerCase().contains("https:")) {
                try {
                    createSSLSocketFactory()?.also {
                        builder.sslSocketFactory(it)
                        builder.hostnameVerifier(TrustAllHostnameVerifier())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, e.message)
                }
            }

            val client = builder.build()

            mRetrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
        }
        return mRetrofit
    }

    private fun  createSSLSocketFactory():SSLSocketFactory? {
        var ssfFactory:SSLSocketFactory ?= null;

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(
                null,
                arrayOf(TrustAllCerts()),
                SecureRandom()
            )
            ssfFactory = sc.socketFactory
        } catch ( e:Exception) {
        }

        return ssfFactory
  }

  private class TrustAllCerts : X509TrustManager {

      @Throws(CertificateException::class)
      override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

      }

      @Throws(CertificateException::class)
      override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
      }

      override fun getAcceptedIssuers(): Array<X509Certificate> {
          return   arrayOf()
      }
  }

  //信任所有的服务器,返回true
  private class TrustAllHostnameVerifier : HostnameVerifier {
      override fun verify(hostname: String?, session: SSLSession?): Boolean {
          return true
      }
  }


}
