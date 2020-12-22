package com.ukdev.carcadasalborghetti.di

import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

abstract class LayerModule {

    protected abstract val module: Module

    protected open val additionalModules: List<Module> = emptyList()

    fun load() {
        loadKoinModules(module + additionalModules)
    }

}