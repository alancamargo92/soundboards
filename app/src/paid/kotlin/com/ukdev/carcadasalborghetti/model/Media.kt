package com.ukdev.carcadasalborghetti.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Media(
        @Expose val id: String,
        @Expose @SerializedName("name") val title: String,
        var position: Int,
        var uri: String
) {

    override fun toString() = title

}