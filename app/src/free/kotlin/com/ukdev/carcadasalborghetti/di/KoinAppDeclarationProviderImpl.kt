package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.data.di.FreeDataModule
import com.ukdev.carcadasalborghetti.ui.di.FreeUiModule

class KoinAppDeclarationProviderImpl : KoinAppDeclarationProvider() {

    override fun loadModules() {
        super.loadModules()
        FreeDataModule.load()
        FreeUiModule.load()
    }

}