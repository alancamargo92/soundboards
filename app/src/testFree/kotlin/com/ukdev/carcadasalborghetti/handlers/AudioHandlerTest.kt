package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.helpers.FileSharingHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AudioHandlerTest {

    @MockK lateinit var mockMediaHelper: MediaHelper
    @MockK lateinit var mockCrashReportManager: CrashReportManager
    @MockK lateinit var mockFileSharingHelper: FileSharingHelper

    private lateinit var audioHandler: AudioHandler

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        audioHandler = AudioHandler(mockMediaHelper, mockCrashReportManager, mockFileSharingHelper)
    }

    @Test
    fun shouldPlayAudio() = runBlocking {
        audioHandler.play(mockk())
    }

    @Test
    fun whenAnExceptionIsThrownWhilePlayingAudio_shouldLogToCrashReport() = runBlocking {
        every { mockMediaHelper.playAudio(any()) } throws Throwable()

        audioHandler.play(mockk())

        verify { mockCrashReportManager.logException(any()) }
    }

    @Test
    fun shouldShareAudio() = runBlocking {
        coEvery { mockFileSharingHelper.getByteStream(any()) } returns mockk()

        audioHandler.share(Media("1", mockk()), MediaType.AUDIO)

        coVerify { mockFileSharingHelper.shareFile(any(), any(), any()) }
    }

    @Test
    fun whenAnExceptionIsThrownWhileSharingAudio_shouldLogToCrashReport() = runBlocking {
        coEvery { mockFileSharingHelper.getByteStream(any()) } throws Throwable()

        audioHandler.share(mockk(), MediaType.AUDIO)

        verify { mockCrashReportManager.logException(any()) }
    }

}