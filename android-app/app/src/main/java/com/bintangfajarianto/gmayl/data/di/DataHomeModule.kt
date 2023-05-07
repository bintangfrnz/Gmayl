package com.bintangfajarianto.gmayl.data.di

import com.bintangfajarianto.gmayl.data.constant.DataHomeConstant
import com.bintangfajarianto.gmayl.data.datasource.home.HomeStorageDataSourceModule
import com.bintangfajarianto.gmayl.data.repository.home.HomeRepository
import com.bintangfajarianto.gmayl.data.repository.home.HomeRepositoryImpl
import com.bintangfajarianto.gmayl.data.repository.home.HomeStorageRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val DataHomeModule: DI.Module
    get() = DI.Module(name = DataHomeConstant.MODULE) {
        importOnce(HomeStorageDataSourceModule)

        bindSingleton<HomeRepository> {
            HomeRepositoryImpl(
                storageRepository = instance<HomeStorageRepository>()
            )
        }
    }
