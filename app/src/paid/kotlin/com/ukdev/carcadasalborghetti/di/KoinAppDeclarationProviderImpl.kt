package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.data.di.PaidDataModule
import com.ukdev.carcadasalborghetti.framework.di.PaidFrameworkModule
import com.ukdev.carcadasalborghetti.ui.di.PaidUiModule

class KoinAppDeclarationProviderImpl : KoinAppDeclarationProvider() {

    override fun loadModules() {
        super.loadModules()
        PaidDataModule().load()
        PaidFrameworkModule().load()
        PaidUiModule().load()
    }

}