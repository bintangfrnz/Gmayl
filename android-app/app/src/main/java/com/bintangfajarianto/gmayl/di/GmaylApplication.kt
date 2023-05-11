package com.bintangfajarianto.gmayl.di

import android.app.Application
import com.bintangfajarianto.gmayl.data.constant.StorageConstant
import com.bintangfajarianto.gmayl.data.storage.KeyValueStorage
import com.bintangfajarianto.gmayl.data.storage.SecureStorage
import com.bintangfajarianto.gmayl.di.constant.AppConstants
import com.bintangfajarianto.gmayl.di.module.RouteDestinationHandlerProviderModule
import com.bintangfajarianto.gmayl.domain.di.auth.LoginStatusUseCaseModule
import com.bintangfajarianto.gmayl.domain.di.auth.LogoutUseCaseModule
import com.bintangfajarianto.gmayl.feature.di.FeatureAuthModule
import com.bintangfajarianto.gmayl.feature.di.FeatureCryptoModule
import com.bintangfajarianto.gmayl.feature.di.FeatureHomeModule
import com.orhanobut.hawk.HawkBuilder
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindEagerSingleton
import org.kodein.di.bindSingleton

class GmaylApplication : Application(), DIAware {
    override val di: DI
        get() = DI.lazy {
            bindEagerSingleton<Application>(tag = AppConstants.APPLICATION_CONTEXT) {
                this@GmaylApplication
            }

            bindSingleton<KeyValueStorage>(tag = StorageConstant.SECURE_STORAGE) {
                SecureStorage(HawkBuilder(this@GmaylApplication))
            }

            importOnce(RouteDestinationHandlerProviderModule)

            // Feature
            importOnce(FeatureAuthModule)
            importOnce(FeatureCryptoModule)
            importOnce(FeatureHomeModule)

            // Specific UseCase
            importOnce(LoginStatusUseCaseModule)
            importOnce(LogoutUseCaseModule)
        }
}
