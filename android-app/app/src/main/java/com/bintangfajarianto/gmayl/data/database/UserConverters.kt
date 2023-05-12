package com.bintangfajarianto.gmayl.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bintangfajarianto.gmayl.core.serializer.decodeTo
import com.bintangfajarianto.gmayl.core.serializer.encodeToString
import com.bintangfajarianto.gmayl.data.model.auth.User

@ProvidedTypeConverter
class UserConverters {
    @TypeConverter
    fun userToString(user: User) : String =
        user.encodeToString(User.serializer())

    @TypeConverter
    fun stringToUser(user: String): User =
        user.decodeTo(User.serializer()) ?: User()
}
