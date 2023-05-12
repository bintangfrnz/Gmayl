package com.bintangfajarianto.gmayl.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bintangfajarianto.gmayl.core.serializer.decodeTo
import com.bintangfajarianto.gmayl.core.serializer.encodeToString
import kotlinx.serialization.serializer

@ProvidedTypeConverter
class PairConverters {
    @TypeConverter
    fun pairToString(pair: Pair<String, String>): String =
        pair.encodeToString(serializer())

    @TypeConverter
    fun stringToPair(pair: String): Pair<String, String> =
        pair.decodeTo(serializer()) ?: ("" to "")
}
