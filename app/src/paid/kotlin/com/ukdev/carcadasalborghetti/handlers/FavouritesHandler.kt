package com.ukdev.carcadasalborghetti.handlers

import android.net.Uri
import com.ukdev.carcadasalborghetti.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.helpers.FileHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class FavouritesHandler(
        mediaHelper: MediaHelper,
        crashReportManager: CrashReportManager,
        fileHelper: FileHelper,
        remoteDataSource: MediaRemoteDataSource,
        ioHelper: IOHelper
) : PaidMediaHandler(mediaHelper, crashReportManager, fileHelper, remoteDataSource, ioHelper) {

    override fun playMedia(link: Uri, media: Media) {
        when (media.type) {
            MediaType.AUDIO -> mediaHelper.playAudio(link)
            MediaType.VIDEO -> mediaHelper.playVideo(link, media.title)
            else -> return
        }
    }

}