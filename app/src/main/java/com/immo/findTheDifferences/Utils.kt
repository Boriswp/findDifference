package com.immo.findTheDifferences

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object Utils {
    fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var len: Int
        try {
            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }
        return outputStream.toString()
    }

    fun prepareToJsonConvert(str: String): String {
        var newStr = str.padStart(str.length + 1, '{')
        newStr = newStr.padEnd(newStr.length + 1, '}')
        newStr = newStr.replace(Const.JSON_ARRAY, "\"${Const.JSON_ARRAY}\"")
        newStr = newStr.replace(Const.JSON_ID, "\"${Const.JSON_ID}\"")
        newStr = newStr.replace(Const.JSON_FOREGROUND, "\"${Const.JSON_FOREGROUND}\"")
        newStr = newStr.replace(Const.JSON_BACKGROUND, "\"${Const.JSON_BACKGROUND}\"")
        newStr = newStr.replace(Const.JSON_PADDING_LEFT, "\"${Const.JSON_PADDING_LEFT}\"")
        newStr = newStr.replace(Const.JSON_PADDING_TOP, "\"${Const.JSON_PADDING_TOP}\"")
        newStr = newStr.replace(Const.JSON_OBJECT_HEIGHT, "\"${Const.JSON_OBJECT_HEIGHT}\"")
        newStr = newStr.replace(Const.JSON_OBJECT_WIDTH, "\"${Const.JSON_OBJECT_WIDTH}\"")
        newStr = newStr.replace("\n\"", ",\n\"")
        newStr = newStr.replace("{,", "{")
        return newStr
    }
}