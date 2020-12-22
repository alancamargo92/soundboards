package com.ukdev.carcadasalborghetti.ui.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ukdev.carcadasalborghetti.R

enum class ErrorType(@DrawableRes val iconRes: Int, @StringRes val textRes: Int) {
    CONNECTION(R.drawable.ic_disconnected, R.string.error_connection),
    NO_FAVOURITES(R.drawable.ic_no_favourites, R.string.error_no_favourites),
    UNKNOWN(R.drawable.ic_error, R.string.error_unknown)
}