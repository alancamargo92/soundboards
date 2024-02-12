package com.ukdev.carcadasalborghetti.ui.model

import androidx.annotation.DrawableRes
import com.ukdev.carcadasalborghetti.R

enum class UiMediaType(@DrawableRes val iconRes: Int) {

    AUDIO(iconRes = R.drawable.ic_audio),
    VIDEO(iconRes = R.drawable.ic_video)
}
