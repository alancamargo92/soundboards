package com.ukdev.carcadasalborghetti.framework.remote

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.framework.remote.api.responses.MediaListResponse
import com.ukdev.carcadasalborghetti.framework.remote.api.responses.MediaResponse
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.TokenHelper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class MediaRemoteDataSourceImplTest {

    @MockK
    lateinit var mockTokenHelper: TokenHelper

    private val mockDropboxApi = MockWebServer()
    private val mockDownloadApi = MockWebServer()

    private lateinit var remoteDataSource: MediaRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        val dropboxBaseUrl = mockDropboxApi.url("/").toString()
        val downloadBaseUrl = mockDownloadApi.url("/downloads/").toString()
        val apiProvider = ApiProvider(
                dropboxBaseUrl,
                downloadBaseUrl,
                mockTokenHelper
        )
        every { mockTokenHelper.getAccessToken() } returns "mock_token"
        remoteDataSource = MediaRemoteDataSourceImpl(apiProvider)
    }

    @Test
    fun shouldListMedia() = runBlocking {
        enqueueSuccessfulMediaListResponse()

        val mediaList = remoteDataSource.listMedia(MediaType.AUDIO)

        assertThat(mediaList.size).isEqualTo(3)
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

    private fun enqueueSuccessfulMediaListResponse() {
        val entries = listOf(
                MediaResponse("id 1", "Media 1.mp3"),
                MediaResponse("id 2", "Media 2.mp3"),
                MediaResponse("id 3", "Media 3.mp3")
        )
        val mediaResponse = MediaListResponse(entries)
        val json = Gson().toJson(mediaResponse)
        val response = MockResponse().setResponseCode(200).setBody(json)
        mockDropboxApi.enqueue(response)
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