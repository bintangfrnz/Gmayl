package com.bintangfajarianto.gmayl.data.python

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class BystarBlockCipher(private val context: Context) {
    fun encryptMessage(plainText: String, key: String): String {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }

        val py = Python.getInstance()
        val pyObject = py.getModule(MAIN)
        val result = pyObject.callAttr(ENCRYPT, plainText, key)

        return result.toString()
    }

    fun decryptMessage(hexText: String, key: String): String {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }

        val py = Python.getInstance()
        val pyObject = py.getModule(MAIN)
        val result = pyObject.callAttr(DECRYPT, hexText, key)

        return result.toString()
    }

    companion object {
        private const val MAIN = "main"
        private const val ENCRYPT = "encrypt"
        private const val DECRYPT = "decrypt"
    }
}
