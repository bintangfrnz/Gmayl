package com.bintangfajarianto.gmayl.data.python

import android.content.Context
import com.bintangfajarianto.gmayl.data.constant.PythonConstant
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

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

    fun sign(privateKey: String, message: String): Pair<Int, Int> {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }

        val py = Python.getInstance()
        val pyObject = py.getModule(PythonConstant.MAIN)
        val result = pyObject.callAttr(PythonConstant.SIGN, privateKey, message)

        return parseStringToIntPair(result.toString())
    }

    fun verify(publicKey: String, message: String, r: Int, s: Int): Boolean {
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
            val listItem = text.drop(2).dropLast(2).split("', '")
            return listItem[0] to listItem[1]
        }

        private fun parseStringToIntPair(text: String): Pair<Int, Int> {
            val listItem = text.drop(1).dropLast(1).split(", ").map { it.toInt() }
            return listItem[0] to listItem[1]
        }

        private fun parseStringToBoolean(text: String): Boolean =
            when (text) {
                "False" -> false
                else -> true
            }
    }
}
