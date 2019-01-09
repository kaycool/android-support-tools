package com.sirit.android.support.lib.network

import android.text.TextUtils.join
import com.sirit.android.support.lib.network.OkCurlString.CONTENT_TYPE
import com.sirit.android.support.lib.network.OkCurlString.FORMAT_BODY
import com.sirit.android.support.lib.network.OkCurlString.FORMAT_HEADER
import com.sirit.android.support.lib.network.OkCurlString.FORMAT_METHOD
import com.sirit.android.support.lib.network.OkCurlString.FORMAT_URL
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.internal.http2.Header
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

/**
 * @author kai.w
 * @des  $des
 */
class OkCurlWorker(request: Request) {

    private var url: String = request.url().toString()
    private var method: String = request.method()
    private var contentType: String = request.body()?.contentType().toString()
    private val headers = arrayListOf<Header>()
    private val body: String

    init {
        // header
        val requestHeaders = request.headers()
        (0 until requestHeaders.size()).forEach {
            headers.add(Header(requestHeaders.name(it), requestHeaders.value(it)))
        }

        // body
        body = getBodyAsString(request.body())
    }

    private fun getBodyAsString(body: RequestBody?): String {
        return try {
            val sink = Buffer()
            val mediaType = body?.contentType()
            val charset = getCharset(mediaType)
            body?.writeTo(sink)
            sink.readString(charset)
        } catch (e: IOException) {
            "Error while reading body: " + e.toString()
        }

    }

    private fun getCharset(mediaType: MediaType?): Charset {
        mediaType?.let {
            return it.charset(Charset.defaultCharset()) ?: Charset.defaultCharset()
        } ?: return Charset.defaultCharset()
    }

    fun work(): String {
        val parts = arrayListOf<String>()
        parts.add("curl")
        parts.add(String.format(FORMAT_METHOD, method.toUpperCase()))

        for (header in headers) {
            val headerPart = String.format(FORMAT_HEADER, header.name.utf8(), header.value.utf8())
            parts.add(headerPart)
        }

        if (contentType.isNotEmpty() && contentType != "null") {
            parts.add(String.format(FORMAT_HEADER, CONTENT_TYPE, contentType))
        }

        if (body.isNotEmpty()) {
            parts.add(String.format(FORMAT_BODY, body))
        }

        parts.add(String.format(FORMAT_URL, url))

        return join(" ", parts)
    }

}