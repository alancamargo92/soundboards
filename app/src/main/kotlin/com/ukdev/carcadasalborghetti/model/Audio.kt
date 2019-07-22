package com.ukdev.carcadasalborghetti.model

import androidx.annotation.RawRes

// TODO: refactor into generic class
data class Audio(
        val title: String,
        val length: String,
        var position: Int,
        @RawRes val fileRes: Int
)