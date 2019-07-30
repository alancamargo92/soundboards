package com.ukdev.carcadasalborghetti.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Media(
        @Expose @SerializedName("path_lower") val path: String,
        @Expose @SerializedName("name") val title: String,
        var position: Int,
        var uri: String
)