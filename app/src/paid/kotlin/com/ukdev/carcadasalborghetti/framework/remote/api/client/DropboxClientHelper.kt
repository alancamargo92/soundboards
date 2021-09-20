package com.ukdev.carcadasalborghetti.framework.remote.api.client

import com.dropbox.core.v2.DbxClientV2

interface DropboxClientHelper {
    fun getClient(): DbxClientV2
}