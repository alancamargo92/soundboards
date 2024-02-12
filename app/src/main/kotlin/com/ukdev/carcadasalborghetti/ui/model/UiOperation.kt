package com.ukdev.carcadasalborghetti.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ukdev.carcadasalborghetti.R

enum class UiOperation(@DrawableRes val icon: Int, @StringRes val text: Int) {

    ADD_TO_FAVOURITES(
        icon = R.drawable.ic_add_to_favourites,
        text = R.string.add_to_favourites
    ),
    REMOVE_FROM_FAVOURITES(
        icon = R.drawable.ic_remove_from_favourites,
        text = R.string.remove_from_favourites
    ),
    SHARE(
        icon = R.drawable.ic_share,
        text = R.string.share
    )
}
