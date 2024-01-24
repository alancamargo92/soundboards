package com.ukdev.carcadasalborghetti.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ukdev.carcadasalborghetti.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Operation(@DrawableRes val icon: Int, @StringRes val text: Int) : Parcelable {
    ADD_TO_FAVOURITES(R.drawable.ic_add_to_favourites, R.string.add_to_favourites),
    REMOVE_FROM_FAVOURITES(R.drawable.ic_remove_from_favourites, R.string.remove_from_favourites),
    SHARE(R.drawable.ic_share, R.string.share)
}