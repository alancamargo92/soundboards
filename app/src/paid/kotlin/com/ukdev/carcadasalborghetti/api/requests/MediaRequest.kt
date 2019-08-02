package com.ukdev.carcadasalborghetti.api.requests

import com.google.gson.Gson

data class MediaRequest(val path: String) {

    override fun toString(): String {
        return Gson().toJson(this)
    }

}