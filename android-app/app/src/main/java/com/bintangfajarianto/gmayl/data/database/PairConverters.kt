package com.bintangfajarianto.gmayl.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bintangfajarianto.gmayl.core.serializer.SerializableBigInteger
import com.bintangfajarianto.gmayl.core.serializer.decodeTo
import com.bintangfajarianto.gmayl.core.serializer.encodeToString
import java.math.BigInteger
import kotlinx.serialization.serializer

@ProvidedTypeConverter
class PairConverters {
    @TypeConverter
    fun pairToString(pair: Pair<SerializableBigInteger, SerializableBigInteger>): String =
        pair.encodeToString(serializer())

    @TypeConverter
    fun stringToPair(pair: String): Pair<SerializableBigInteger, SerializableBigInteger> =
        pair.decodeTo(serializer())
            ?: Pair(BigInteger.valueOf(0L), BigInteger.valueOf(0L))
}
