package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import com.ukdev.carcadasalborghetti.model.Carcada

abstract class CarcadaRepository(protected val context: Context) {
    abstract fun getCarcadas(): List<Carcada>
}