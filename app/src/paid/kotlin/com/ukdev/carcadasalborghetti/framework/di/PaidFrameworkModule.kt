package com.ukdev.carcadasalborghetti.framework.di

import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.framework.local.db.FavouritesDatabaseProvider
import com.ukdev.carcadasalborghetti.framework.remote.api.client.DropboxClientHelperImpl
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class PaidFrameworkModule : LayerModule() {

    override val module = module {
        factory { FavouritesDatabaseProvider.getInstance(androidContext()).provideDatabase() }
        factory { IOHelper(crashReportManager = get()) }
        single { DropboxClientHelperImpl().getClient() }
    }

}