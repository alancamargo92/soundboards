package com.ukdev.carcadasalborghetti.api.requests

import com.google.gson.Gson

data class MediaRequest(val id: String) {

    override fun toString(): String {
        return Gson().toJson(this)
    }

}