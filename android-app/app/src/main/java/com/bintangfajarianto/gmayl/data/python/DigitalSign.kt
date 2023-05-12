package com.bintangfajarianto.gmayl.data.python

import android.content.Context
import com.bintangfajarianto.gmayl.data.constant.PythonConstant
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import io.github.aakira.napier.Napier

class DigitalSign(private val context: Context) {
    fun generateKeyPair(): Pair<String, String> {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }

        val py = Python.getInstance()
        val pyObject = py.getModule(PythonConstant.MAIN)
        val result = pyObject.callAttr(PythonConstant.GENERATE_KEY_PAIR)

        return parseStringToStringPair(result.toString())
    }

    fun sign(privateKey: String, message: String): Pair<String, String> {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }

        val py = Python.getInstance()
        val pyObject = py.getModule(PythonConstant.MAIN)
        val result = pyObject.callAttr(PythonConstant.SIGN, privateKey, message)

        return parseStringToStringPair(result.toString())
    }

    fun verify(publicKey: String, message: String, r: String, s: String): Boolean {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }

        val py = Python.getInstance()
        val pyObject = py.getModule(PythonConstant.MAIN)
        val result = pyObject.callAttr(PythonConstant.VERIFY, publicKey, message, r, s)

        return parseStringToBoolean(result.toString())
    }

    companion object {
        private fun parseStringToStringPair(text: String): Pair<String, String> {
            Napier.i("text: $text", tag = "DEBUG")
            val listItem = text.drop(2).dropLast(2).split("', '")
            Napier.i("listItem: $listItem", tag = "DEBUG")
            return listItem[0] to listItem[1]
        }

        private fun parseStringToBoolean(text: String): Boolean =
            when (text) {
                "False" -> false
                else -> true
            }
    }
}
