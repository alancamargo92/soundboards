package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
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
    @MockK lateinit var mockRemoteDataSource: MediaRemoteDataSource

    private lateinit var audioHandler: AudioHandler

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        audioHandler = AudioHandler(
                mockMediaHelper,
                mockCrashReportManager,
                mockFileSharingHelper,
                mockRemoteDataSource
        )
    }

    @Test
    fun shouldPlayAudio() = runBlocking {
        coEvery { mockRemoteDataSource.getStreamLink(any()) } returns "mock/link"

        audioHandler.play(Media("1", "Media 1"))
    }

    @Test
    fun whenAnExceptionIsThrownWhilePlayingAudio_shouldLogToCrashReport() = runBlocking {
        coEvery { mockRemoteDataSource.getStreamLink(any()) } throws Throwable()

        audioHandler.play(Media("1", "Media 1"))

        verify { mockCrashReportManager.logException(any()) }
    }

    @Test
    fun shouldShareAudio() = runBlocking {
        coEvery { mockRemoteDataSource.download(any()) } returns mockk()

        audioHandler.share(Media("1", "Media 1"), MediaType.AUDIO)

        coVerify { mockFileSharingHelper.shareFile(any(), any(), any()) }
    }

    @Test
    fun whenAnExceptionIsThrownWhileSharingAudio_shouldLogToCrashReport() = runBlocking {
        coEvery { mockRemoteDataSource.download(any()) } throws Throwable()

        audioHandler.share(Media("1", "Media 1"), MediaType.AUDIO)

        verify { mockCrashReportManager.logException(any()) }
    }

}