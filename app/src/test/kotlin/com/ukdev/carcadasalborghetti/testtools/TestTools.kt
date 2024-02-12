package com.ukdev.carcadasalborghetti.testtools

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import java.io.File

fun stubMedia(type: MediaType = MediaType.AUDIO) = Media(
    id = "android.resource://com.ukdev.carcadasalborghetti/12345",
    title = "Title.mp3",
    type = type
)

fun stubMediaList() = listOf(stubMedia())

fun stubFile() = File("mock-path")
