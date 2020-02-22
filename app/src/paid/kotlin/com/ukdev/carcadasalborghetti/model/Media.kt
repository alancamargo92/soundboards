package com.ukdev.carcadasalborghetti.model

import com.google.gson.annotations.SerializedName

data class Media(
        val id: String,
        @SerializedName("name") val title: String
) {

    var position: Int = 0

    override fun toString() = title

}