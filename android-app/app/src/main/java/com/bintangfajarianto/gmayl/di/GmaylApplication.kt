package com.bintangfajarianto.gmayl.di

import android.app.Application
import com.bintangfajarianto.gmayl.di.constant.AppConstants
import com.bintangfajarianto.gmayl.di.module.RouteDestinationHandlerProviderModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindEagerSingleton

class GmaylApplication : Application(), DIAware {
    override val di: DI
        get() = DI.lazy {
            bindEagerSingleton<Application>(tag = AppConstants.APPLICATION_CONTEXT) {
                this@GmaylApplication
            }

            importOnce(RouteDestinationHandlerProviderModule)
        }
}
