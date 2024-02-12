package com.ukdev.carcadasalborghetti.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ukdev.carcadasalborghetti.R

enum class UiError(@DrawableRes val iconRes: Int, @StringRes val textRes: Int) {

    CONNECTION(
        iconRes = R.drawable.ic_disconnected,
        textRes = R.string.error_connection
    ),
    NO_FAVOURITES(
        iconRes = R.drawable.ic_no_favourites,
        textRes = R.string.error_no_favourites
    ),
    UNKNOWN(
        iconRes = R.drawable.ic_error,
        textRes = R.string.error_unknown
    )
}
