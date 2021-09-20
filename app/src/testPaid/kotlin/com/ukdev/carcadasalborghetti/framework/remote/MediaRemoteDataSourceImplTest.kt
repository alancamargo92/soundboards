package com.ukdev.carcadasalborghetti.framework.remote

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class MediaRemoteDataSourceImplTest {

    private val mockDropboxApi = MockWebServer()
    private val mockDownloadApi = MockWebServer()

    private lateinit var remoteDataSource: MediaRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun shouldDownloadMedia() = runBlocking {
        enqueueSuccessfulDownloadResponse()

        val byteStream = remoteDataSource.download("media-id")

        assertThat(byteStream).isNotNull()
    }

    @Test(expected = HttpException::class)
    fun whenMediaListRequestFails_shouldThrowException() {
        enqueueDropboxApiErrorResponse()

        runBlocking {
            remoteDataSource.listMedia(MediaType.AUDIO)
        }
    }

    @Test(expected = HttpException::class)
    fun whenDownloadRequestFails_shouldThrowException() {
        enqueueDownloadApiErrorResponse()

        runBlocking {
            remoteDataSource.download("media-id")
        }
    }

    private fun enqueueSuccessfulDownloadResponse() {
        val responseBody = mockk<ResponseBody>()
        val json = Gson().toJson(responseBody)
        val response = MockResponse().setResponseCode(200).setBody(json)
        mockDownloadApi.enqueue(response)
    }

    private fun enqueueDropboxApiErrorResponse() {
        val response = MockResponse().setResponseCode(404)
        mockDropboxApi.enqueue(response)
    }

    private fun enqueueDownloadApiErrorResponse() {
        val response = MockResponse().setResponseCode(404)
        mockDownloadApi.enqueue(response)
    }

}