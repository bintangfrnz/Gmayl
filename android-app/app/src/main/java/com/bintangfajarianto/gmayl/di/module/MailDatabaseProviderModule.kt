package com.bintangfajarianto.gmayl.di.module

import androidx.room.Room
import com.bintangfajarianto.gmayl.data.database.MailDatabase
import com.bintangfajarianto.gmayl.data.database.UserConverters
import com.bintangfajarianto.gmayl.di.constant.AppConstants
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val MailDatabaseProviderModule: DI.Module
    get() = DI.Module(name = "MailDatabaseProviderModule") {

        bindSingleton {
            Room.databaseBuilder(
                context = instance(tag = AppConstants.APPLICATION_CONTEXT),
                klass = MailDatabase::class.java,
                name = MailDatabase.DATABASE_NAME,
            ).addTypeConverter(UserConverters()).build()
        }
    }
