package com.ukdev.carcadasalborghetti.api.requests

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class MediaRequest(@SerializedName("path") val mediaId: String) {

    override fun toString(): String {
        return Gson().toJson(this)
    }

}