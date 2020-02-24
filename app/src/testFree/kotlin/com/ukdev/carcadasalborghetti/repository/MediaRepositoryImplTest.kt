package com.ukdev.carcadasalborghetti.repository

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.data.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.model.GenericError
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.model.Success
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MediaRepositoryImplTest {

    @MockK lateinit var mockCrashReportManager: CrashReportManager
    @MockK lateinit var mockLocalDataSource: MediaLocalDataSource

    private lateinit var repository: MediaRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        repository = MediaRepositoryImpl(mockCrashReportManager, mockLocalDataSource)
    }

    @Test
    fun shouldGetMediaList() = runBlocking {
        val uri1 = mockk<Uri>()
        val uri2 = mockk<Uri>()
        every { mockLocalDataSource.getTitles() } returns arrayOf("title 1", "title 2")
        coEvery { mockLocalDataSource.getAudioUris() } returns arrayOf(uri1, uri2)

        val expected = listOf(Media("title 1", uri1), Media("title 2", uri2))

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(Success::class.java)
        require(result is Success)
        assertThat(result.body).isEqualTo(expected)
    }

    @Test
    fun whenAnExceptionIsThrown_shouldLogToCrashReport() = runBlocking {
        every { mockLocalDataSource.getTitles() } throws Throwable()

        repository.getMedia(MediaType.AUDIO)

        verify { mockCrashReportManager.logException(any()) }
    }

    @Test
    fun whenAnExceptionIsThrown_shouldReturnGenericError() = runBlocking {
        every { mockLocalDataSource.getTitles() } throws Throwable()

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(GenericError::class.java)
    }

}