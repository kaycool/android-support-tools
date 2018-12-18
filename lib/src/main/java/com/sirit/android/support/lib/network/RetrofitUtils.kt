package com.sirit.android.support.lib.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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

    fun getRetrofit(baseUrl: String): Retrofit {
        if (!this::mRetrofit.isInitialized || baseUrl != mRetrofit.baseUrl().toString()) {
            val builder = OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//                    .addInterceptor(interceptor)//拦截器处理通用Header
                .addInterceptor(loggingInterceptor) //日志处理
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)

//            if (!baseUrl.isEmpty() && baseUrl.toLowerCase().contains("https:")) {
//                try {
//                    builder.sslSocketFactory(getSSLSocketFactory(intArrayOf(R.raw.azoyaclub)))
//                    builder.hostnameVerifier(HtcHostnameVerifier(arrayOf<String>(BASE_URL_HOST)))
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    Log.e(TAG, e.message)
//                }
//            }

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

    fun getRetrofit(baseUrl: String, interceptor: () -> Interceptor): Retrofit {
        if (!this::mRetrofit.isInitialized || baseUrl != mRetrofit.baseUrl().toString()) {
            val builder = OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(interceptor())//拦截器处理通用Header
                .addInterceptor(loggingInterceptor) //日志处理
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)

//            if (!baseUrl.isEmpty() && baseUrl.toLowerCase().contains("https:")) {
//                try {
//                    builder.sslSocketFactory(getSSLSocketFactory(intArrayOf(R.raw.azoyaclub)))
//                    builder.hostnameVerifier(HtcHostnameVerifier(arrayOf<String>(BASE_URL_HOST)))
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    Log.e(TAG, e.message)
//                }
//            }

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


//    @Throws(Exception::class)
//    private fun getSSLSocketFactory(certificates: IntArray): SSLSocketFactory {
//        val certificateFactory = CertificateFactory.getInstance("X.509")
//        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
//        keyStore.load(null, null)
//
//        for (i in certificates.indices) {
//            val certificate = HtcApplication.getInstance().getResources().openRawResource(certificates[i])
//            keyStore.setCertificateEntry(i.toString(), certificateFactory.generateCertificate(certificate))
//            IoUtils.closeStream(certificate)
//        }
//        val sslContext = SSLContext.getInstance("TLS")
//        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
//        trustManagerFactory.init(keyStore)
//        sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
//        return sslContext.socketFactory
//    }

}
