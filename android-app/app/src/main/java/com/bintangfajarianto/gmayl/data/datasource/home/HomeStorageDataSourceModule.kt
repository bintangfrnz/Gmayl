package com.bintangfajarianto.gmayl.data.datasource.home

import com.bintangfajarianto.gmayl.data.constant.DataHomeConstant
import com.bintangfajarianto.gmayl.data.repository.home.HomeStorageRepository
import com.bintangfajarianto.gmayl.di.module.MailDatabaseProviderModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val HomeStorageDataSourceModule: DI.Module
    get() = DI.Module(name = DataHomeConstant.STORAGE_DATA_SOURCE_MODULE) {
        importOnce(MailDatabaseProviderModule)

        bindSingleton<HomeStorageRepository> {
            HomeStorageDataSource(
                mailDatabase = instance()
            )
        }
    }
