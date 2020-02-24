package com.ukdev.carcadasalborghetti.repository

import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class MediaRepositoryImplTest {

    @MockK lateinit var mockCrashReportManager: CrashReportManager
    @MockK lateinit var mockRemoteDataSource: MediaRemoteDataSource

    private lateinit var repository: MediaRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        repository = MediaRepositoryImpl(mockCrashReportManager, mockRemoteDataSource)
    }

    @Test
    fun shouldGetMediaList() = runBlocking {
        val expected = listOf(
                Media("1", "media 1"),
                Media("2", "media 2"),
                Media("3", "media 3")
        )
        coEvery { mockRemoteDataSource.listMedia(any()) } returns expected

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(Success::class.java)
        require(result is Success)
        assertThat(result.body).isEqualTo(expected)
    }

    @Test
    fun whenAHttpExceptionIsThrown_shouldLogToCrashReport() = runBlocking {
        coEvery {
            mockRemoteDataSource.listMedia(any())
        } throws HttpException(mockk(relaxed = true))

        repository.getMedia(MediaType.AUDIO)

        verify { mockCrashReportManager.logException(any<HttpException>()) }
    }

    @Test
    fun whenAHttpExceptionIsThrown_shouldReturnGenericError() = runBlocking {
        coEvery {
            mockRemoteDataSource.listMedia(any())
        } throws HttpException(mockk(relaxed = true))

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(GenericError::class.java)
    }

    @Test
    fun whenAnIOExceptionIsThrown_shouldLogToCrashReport() = runBlocking {
        coEvery { mockRemoteDataSource.listMedia(any()) } throws IOException()

        repository.getMedia(MediaType.AUDIO)

        verify { mockCrashReportManager.logException(any<IOException>()) }
    }

    @Test
    fun whenAnIOExceptionIsThrown_shouldReturnNetworkError() = runBlocking {
        coEvery { mockRemoteDataSource.listMedia(any()) } throws IOException()

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(NetworkError::class.java)
    }

    @Test
    fun whenARandomExceptionIsThrown_shouldLogToCrashReport() = runBlocking {
        coEvery { mockRemoteDataSource.listMedia(any()) } throws Throwable()

        repository.getMedia(MediaType.AUDIO)

        verify { mockCrashReportManager.logException(any()) }
    }

    @Test
    fun whenARandomExceptionIsThrown_shouldReturnGenericError() = runBlocking {
        coEvery { mockRemoteDataSource.listMedia(any()) } throws Throwable()

        val result = repository.getMedia(MediaType.AUDIO)

        assertThat(result).isInstanceOf(GenericError::class.java)
    }

}