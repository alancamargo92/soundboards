package com.ukdev.carcadasalborghetti.framework.media

import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import com.ukdev.carcadasalborghetti.ui.media.AudioHandler
import com.ukdev.carcadasalborghetti.ui.media.MediaHelper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.InputStream

class AudioHandlerTest {

    @MockK lateinit var mockMediaHelper: MediaHelper
    @MockK lateinit var mockCrashReportManager: CrashReportManager
    @MockK lateinit var mockFileHelper: FileHelper

    private lateinit var audioHandler: AudioHandler

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        audioHandler = AudioHandler(mockMediaHelper, mockCrashReportManager, mockFileHelper)
    }

    @Test
    fun shouldPlayAudio() = runBlocking {
        audioHandler.play(Media("Title", mockk()))

        verify(exactly = 0) { mockCrashReportManager.logException(any()) }
    }

    @Test
    fun whenAnExceptionIsThrownWhilePlayingAudio_shouldLogToCrashReport() = runBlocking {
        every { mockMediaHelper.playAudio(any()) } throws Throwable()

        audioHandler.play(mockk())

        verify { mockCrashReportManager.logException(any()) }
    }

    @Test
    fun shouldShareAudio() = runBlocking {
        coEvery { mockFileHelper.getByteStream(any()) } returns mockk()

        audioHandler.share(Media("1", mockk()))

        coVerify { mockFileHelper.shareFile(any<InputStream>(), any()) }
    }

    @Test
    fun whenAnExceptionIsThrownWhileSharingAudio_shouldLogToCrashReport() = runBlocking {
        coEvery { mockFileHelper.getByteStream(any()) } throws Throwable()

        audioHandler.share(mockk())

        verify { mockCrashReportManager.logException(any()) }
    }

}