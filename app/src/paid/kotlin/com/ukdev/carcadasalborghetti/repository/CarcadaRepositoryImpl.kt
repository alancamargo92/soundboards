package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import com.ukdev.carcadasalborghetti.model.Carcada

class CarcadaRepositoryImpl(context: Context) : CarcadaRepository(context) {

    override fun getCarcadas(): List<Carcada> {
        // TODO
        return emptyList()
    }

}