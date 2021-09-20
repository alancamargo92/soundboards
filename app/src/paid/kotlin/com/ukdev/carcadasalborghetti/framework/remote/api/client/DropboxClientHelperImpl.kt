package com.ukdev.carcadasalborghetti.framework.remote.api.client

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.ukdev.carcadasalborghetti.BuildConfig

class DropboxClientHelperImpl : DropboxClientHelper {

    override fun getClient(): DbxClientV2 {
        val config = DbxRequestConfig.newBuilder(
            "${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_NAME}"
        ).build()
        return DbxClientV2(config, BuildConfig.ACCESS_TOKEN)
    }

}