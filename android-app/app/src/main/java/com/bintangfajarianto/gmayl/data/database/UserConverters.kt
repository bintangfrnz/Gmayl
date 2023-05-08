package com.bintangfajarianto.gmayl.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bintangfajarianto.gmayl.data.decodeTo
import com.bintangfajarianto.gmayl.data.encodeToString
import com.bintangfajarianto.gmayl.data.model.auth.User

@ProvidedTypeConverter
class UserConverters {
    @TypeConverter
    fun toString(user: User) : String =
        user.encodeToString(User.serializer())

    @TypeConverter
    fun fromUser(user: String): User =
        user.decodeTo(User.serializer()) ?: User()
}
