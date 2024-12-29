package com.ukdev.carcadasalborghetti.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ukdev.carcadasalborghetti.R

enum class MediaListTab(@StringRes val textRes: Int, @DrawableRes val iconRes: Int) {

    AUDIOS(textRes = R.string.audios, iconRes = R.drawable.ic_tab_audio),
    VIDEOS(textRes = R.string.videos, iconRes = R.drawable.ic_tab_video),
    FAVOURITES(textRes = R.string.favourites, iconRes = R.drawable.ic_tab_favourites);
}
