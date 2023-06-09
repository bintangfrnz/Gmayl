package com.bintangfajarianto.gmayl.feature.di

import com.bintangfajarianto.gmayl.feature.constant.FeatureHomeConstants
import com.bintangfajarianto.gmayl.feature.vm.home.HomeViewModelModule
import com.bintangfajarianto.gmayl.feature.vm.home.detailmail.MailDetailViewModelModule
import com.bintangfajarianto.gmayl.feature.vm.home.sendmail.SendMailViewModelModule
import org.kodein.di.DI

val FeatureHomeModule: DI.Module
    get() = DI.Module(name = FeatureHomeConstants.MODULE) {
        importOnce(HomeViewModelModule)
        importOnce(MailDetailViewModelModule)
        importOnce(SendMailViewModelModule)
    }
