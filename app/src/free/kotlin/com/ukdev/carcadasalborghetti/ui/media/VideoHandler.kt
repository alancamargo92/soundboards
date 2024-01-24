package com.ukdev.carcadasalborghetti.ui.media

import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.data.tools.FileHelper
import javax.inject.Inject

class VideoHandler @Inject constructor(
    mediaHelper: MediaHelper,
    logger: Logger,
    fileSharingHelper: FileHelper
) : MediaHandler(mediaHelper, logger, fileSharingHelper) {

    override suspend fun play(media: Media) = Unit

    override suspend fun share(media: Media) = Unit
}
