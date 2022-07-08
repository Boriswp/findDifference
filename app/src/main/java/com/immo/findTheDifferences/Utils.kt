package com.immo.findTheDifferences

import android.content.Context
import java.io.IOException
import java.io.InputStream

object Utils {
    fun readXMLinString(fileName: String?, c: Context): String {
        return try {
            val `is`: InputStream? = fileName?.let { c.assets.open(it) }
            val size: Int = `is`?.available() ?: 0
            val buffer = ByteArray(size)
            `is`?.read(buffer)
            `is`?.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}