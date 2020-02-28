package com.ukdev.carcadasalborghetti.handlers

import com.ukdev.carcadasalborghetti.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.helpers.FileHelper
import com.ukdev.carcadasalborghetti.helpers.MediaHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException
import java.io.InputStream

class AudioHandlerTest {

    @MockK lateinit var mockMediaHelper: MediaHelper
    @MockK lateinit var mockCrashReportManager: CrashReportManager
    @MockK lateinit var mockFileHelper: FileHelper
    @MockK lateinit var mockRemoteDataSource: MediaRemoteDataSource

    private lateinit var audioHandler: AudioHandler

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        audioHandler = AudioHandler(
                mockMediaHelper,
                mockCrashReportManager,
                mockFileHelper,
                mockRemoteDataSource,
                IOHelper(mockCrashReportManager)
        )
    }

    @Test
    fun whenPlaying_shouldPrioritiseCache() = runBlocking {
        coEvery { mockFileHelper.getFileUri(any()) } returns mockk()

        audioHandler.play(Media("id", "Title", MediaType.AUDIO))

        coVerify(exactly = 0) { mockRemoteDataSource.download(any()) }
    }

    @Test
    fun whenAudioIsNotInCache_shouldDownloadBeforePlaying() = runBlocking {
        coEvery { mockFileHelper.getFileUri(any()) } throws FileNotFoundException()
        coEvery { mockRemoteDataSource.download(any()) } returns mockk()
        coEvery { mockFileHelper.getFileUri(any(), any()) } returns mockk()

        audioHandler.play(Media("id", "Title", MediaType.AUDIO))

        coVerify { mockRemoteDataSource.download(any()) }
    }

    @Test
    fun shouldShareAudio() = runBlocking {
        coEvery { mockRemoteDataSource.download(any()) } returns mockk()

        audioHandler.share(Media("1", "Media 1", MediaType.AUDIO))

        coVerify { mockFileHelper.shareFile(any<InputStream>(), any()) }
    }

    @Test
    fun whenSharing_shouldPrioritiseCache() = runBlocking {
        coEvery { mockFileHelper.getFileUri(any()) } returns mockk()

        audioHandler.share(Media("id", "Title", MediaType.AUDIO))

        coVerify(exactly = 0) { mockRemoteDataSource.download(any()) }
    }

    @Test
    fun whenAudioIsNotInCache_shouldDownloadBeforeSharing() = runBlocking {
        coEvery { mockFileHelper.getFileUri(any()) } throws FileNotFoundException()
        coEvery { mockRemoteDataSource.download(any()) } returns mockk()

        audioHandler.share(Media("id", "Title", MediaType.AUDIO))

        coVerify { mockRemoteDataSource.download(any()) }
    }

    @Test
    fun whenAnExceptionIsThrownWhileSharingAudio_shouldLogToCrashReport() = runBlocking {
        coEvery { mockRemoteDataSource.download(any()) } throws Throwable()

        audioHandler.share(Media("1", "Media 1", MediaType.AUDIO))

        verify { mockCrashReportManager.logException(any()) }
    }

}