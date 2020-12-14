package com.ukdev.carcadasalborghetti.data.repository

import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.data.entities.GenericError
import com.ukdev.carcadasalborghetti.data.entities.Success
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
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
        coEvery { mockLocalDataSource.getMediaList() } returns listOf(
                Media("Media 1", mockk()),
                Media("Media 2", mockk()),
                Media("Media 3", mockk())
        )

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(Success::class.java)
        require(result is Success)
        assertThat(result.body.size).isEqualTo(3)
    }

    @Test
    fun whenAnExceptionIsThrown_shouldLogToCrashReport() = runBlocking {
        coEvery { mockLocalDataSource.getMediaList() } throws Throwable()

        repository.getMedia(MediaType.AUDIO)

        verify { mockCrashReportManager.logException(any()) }
    }

    @Test
    fun whenAnExceptionIsThrown_shouldReturnGenericError() = runBlocking {
        coEvery { mockLocalDataSource.getMediaList() } throws Throwable()

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(GenericError::class.java)
    }

    @Test
    fun availableOperationsShouldOnlyBeShare() {
        val media = Media("Media 1", mockk())

        val operations = runBlocking {
            repository.getAvailableOperations(media)
        }

        assertThat(operations).containsExactly(Operation.SHARE)
    }

}