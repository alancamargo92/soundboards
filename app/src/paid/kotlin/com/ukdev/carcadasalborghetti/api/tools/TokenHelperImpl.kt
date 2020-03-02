package com.ukdev.carcadasalborghetti.api.tools

import com.ukdev.carcadasalborghetti.BuildConfig

class TokenHelperImpl : TokenHelper {

    override fun getAccessToken(): String = BuildConfig.ACCESS_TOKEN

}