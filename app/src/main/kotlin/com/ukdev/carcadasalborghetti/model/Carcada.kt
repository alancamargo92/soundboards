package com.ukdev.carcadasalborghetti.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class Carcada(
        @StringRes val titleRes: Int,
        @StringRes val lengthRes: Int,
        @RawRes val audioFileRes: Int,
        val position: Int
)