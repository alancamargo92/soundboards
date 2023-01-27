package com.ukdev.carcadasalborghetti.framework.di

import androidx.room.Room
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.framework.local.db.FavouritesDatabaseProvider
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.framework.tools.PaidFileHelper
import com.ukdev.carcadasalborghetti.framework.tools.PaidFileHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class PaidFrameworkModule : LayerModule() {

    override val module = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                FavouritesDatabaseProvider::class.java,
                "favourites_database"
            ).fallbackToDestructiveMigration().build().provideDatabase()
        }
        factory { IOHelper(crashReportManager = get()) }
        factory<PaidFileHelper> { PaidFileHelperImpl(context = androidContext()) }
        single { Firebase.storage.reference.root }
    }

}