package com.ukdev.carcadasalborghetti

import android.app.Application
import com.google.firebase.FirebaseApp
import com.ukdev.carcadasalborghetti.di.KoinAppDeclarationProviderImpl
import org.koin.core.context.startKoin

@Suppress("registered")
open class CarcadasAlborghettiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    protected fun startDependencyInjection() {
        val provider = KoinAppDeclarationProviderImpl()
        startKoin(appDeclaration = provider.provideAppDeclaration(this))
    }

}