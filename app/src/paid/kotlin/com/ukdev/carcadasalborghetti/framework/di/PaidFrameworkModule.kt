package com.ukdev.carcadasalborghetti.framework.di

import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.framework.local.db.FavouritesDatabaseProvider
import com.ukdev.carcadasalborghetti.framework.remote.api.BASE_URL
import com.ukdev.carcadasalborghetti.framework.remote.api.BASE_URL_DOWNLOADS
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.TokenHelper
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.TokenHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object PaidFrameworkModule : LayerModule() {

    override val module = module {
        factory { FavouritesDatabaseProvider.getInstance(androidContext()).provideDatabase() }
        factory {
            ApiProvider(
                    dropboxBaseUrl = BASE_URL,
                    downloadBaseUrl = BASE_URL_DOWNLOADS,
                    tokenHelper = get()
            )
        }
        factory { IOHelper(crashReportManager = get()) }
        factory<TokenHelper> { TokenHelperImpl() }

    }

}