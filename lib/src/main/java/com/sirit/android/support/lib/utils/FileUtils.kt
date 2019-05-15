package com.sirit.android.support.lib.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.*
import java.util.*

/**
 * @author kai.w
 * @des  $des
 */
object FileUtils {

    private val supportedType = mutableMapOf("txt" to "text/plain",
        "doc" to "application/msword",
        "docx" to "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "pdf" to "application/pdf",
        "xls" to "application/vnd.ms-excel",
        "xlsx" to "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "ppt" to "application/vnd.ms-powerpoint",
        "pptx" to "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "epub" to "application/epub+zip")
    private val supportedImage = mutableMapOf("gif" to "image/gif",
        "jpeg" to "image/jpeg",
        "jpg" to "image/jpeg",
        "png" to "image/png",
        "bmp" to "image/x-ms-bmp")
    private val supportMedia = mutableMapOf("m4a" to "audio/aac", "mp3" to "audio/mpeg", "wav" to "audio/x-wav", "mp4" to "video/mp4")

    init {
        supportedType.putAll(supportedImage)
        supportedType.putAll(supportMedia)
    }

    fun getContentType(path: String): String {
        if (path.indexOf(".") == -1) {
            return supportedType[path.toLowerCase()] ?: ""
        }
        val suffix = path.substring(path.lastIndexOf(".") + 1)
        return supportedType[suffix.toLowerCase()] ?: getUnKnownType(path)
    }

    fun getUnKnownType(path: String): String {
        if (!path.startsWith("http")) {
            val file = File(path)
            if (file.exists()) {
                try {
                    val m = MediaMetadataRetriever()
                    m.setDataSource(path)
                    return m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
                } catch (e: Exception) {
                }
            }
        }
        return ""
    }

    fun getFileSuffix(contentType: String) = if (supportedType.containsValue(contentType.toLowerCase())) {
        supportedType.keys.firstOrNull { supportedType[it] == contentType }
    } else {
        "unKnow"
    }

    fun getRealFilePath(context: Context, uri: Uri): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, uri) -> {
                when {
                    isExternalStorageDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]
                        return if ("primary".equals(type, ignoreCase = true)) {
                            Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                        } else {
                            ""
                        }
                    }

                    isDownloadsDocument(uri) -> {
                        val id = DocumentsContract.getDocumentId(uri)
                        if (!id.isNullOrEmpty()) {
                            if (id.startsWith("raw:")) {
                                return id.replaceFirst("raw:", "")
                            }
                        }

                        var name = ""
                        var contentType = ""
                        try {
                            contentType = context.contentResolver.getType(uri) ?: ""
                            context.contentResolver.query(uri, null, null, null, null)?.use {
                                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                it.moveToFirst()
                                name = it.getString(nameIndex)
                                it.close()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return getDataColumn(context, ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), id.toLong())
                            , null, null)
                            ?: getPathFromInputStreamUri(context, uri, if (name.isBlank()) {
                                "$id.${FileUtils.getFileSuffix(contentType)}"
                            } else {
                                name
                            })
                            ?: ""
                    }

                    isMediaDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]
                        val contentUri: Uri? = when (type) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }

                        val selection = "_id=?"
                        val selectionArgs = arrayOf(split[1])
                        return getDataColumn(context, contentUri, selection, selectionArgs) ?: ""
                    }

                    else -> ""
                }
            }

            "content".equals(uri.scheme, ignoreCase = true) -> {
                return when {
                    isGooglePhotosUri(uri) -> uri.lastPathSegment ?: ""
                    else -> getDataColumn(context, uri, null, null)
                        ?: getPathFromInputStreamUri(context, uri) ?: ""
                }
            }

            "file".equals(uri.scheme, ignoreCase = true) -> {
                return uri.path ?: ""
            }

            else -> ""
        }
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(ctx: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = MediaStore.MediaColumns.DATA
        val projection = arrayOf(column)

        try {
            cursor = uri?.let {
                ctx.contentResolver.query(uri, projection, selection, selectionArgs, null)
            }
            cursor?.also {
                it.moveToFirst()
                return cursor.getString(it.getColumnIndexOrThrow(column))
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun getPathFromInputStreamUri(ctx: Context, uri: Uri?, fileName: String = ""): String? {
        var filePath: String? = null
        var inputStream: FileInputStream? = null
        var outputStream: FileOutputStream? = null
        var tempFileName = fileName
        if (tempFileName.isEmpty()) {
            tempFileName = uri?.let {
                "${UUID.randomUUID()}.${getFileSuffix(ctx.contentResolver.getType(it) ?: "")}"
            } ?: return ""
        }
        try {
            val openFileDescriptor = ctx.contentResolver.openFileDescriptor(uri ?: return "", "r")
            val outputFile = File(ctx.cacheDir, tempFileName)
            inputStream = FileInputStream(openFileDescriptor.fileDescriptor)
            outputStream = FileOutputStream(outputFile)

            val b = ByteArray(1024)
            var n = 0
            while (inputStream.read(b).apply { n = this } != -1) {
                outputStream.write(b, 0, n)
            }
            filePath = outputFile.path

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return filePath ?: ""
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return uri.authority == "com.google.android.apps.photos.content"
    }


}