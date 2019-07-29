package com.ukdev.carcadasalborghetti.model

import android.net.Uri
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Media(
        @Expose @SerializedName("path_lower") val path: String,
        @Expose @SerializedName("name") val title: String,
        val length: String,
        var position: Int,
        val uri: Uri
)