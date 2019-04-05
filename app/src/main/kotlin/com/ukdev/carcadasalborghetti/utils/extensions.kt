package com.ukdev.carcadasalborghetti.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KClass

fun <T: ViewModel> AppCompatActivity.provideViewModel(clazz: KClass<T>) = lazy {
    ViewModelProviders.of(this).get(clazz.java)
}