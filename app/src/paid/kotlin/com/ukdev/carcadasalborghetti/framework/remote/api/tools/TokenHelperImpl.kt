package com.ukdev.carcadasalborghetti.framework.remote.api.tools

import com.ukdev.carcadasalborghetti.BuildConfig

class TokenHelperImpl : TokenHelper {

    override fun getAccessToken(): String = BuildConfig.ACCESS_TOKEN

}