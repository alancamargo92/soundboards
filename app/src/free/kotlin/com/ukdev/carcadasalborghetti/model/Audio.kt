package com.ukdev.carcadasalborghetti.model

import androidx.annotation.RawRes

data class Audio(
        override val title: String,
        override val length: String,
        override var position: Int,
        @RawRes val fileRes: Int
) : Media(title, length, position, "")