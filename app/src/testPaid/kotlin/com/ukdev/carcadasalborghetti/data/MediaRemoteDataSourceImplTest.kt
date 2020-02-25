package com.ukdev.carcadasalborghetti.data

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.ukdev.carcadasalborghetti.api.responses.MediaResponse
import com.ukdev.carcadasalborghetti.api.responses.StreamLinkResponse
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.api.tools.TokenHelper
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Ignore
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
    @Ignore("Streaming will be replaced with download soon")
    fun shouldGetStreamLink() = runBlocking {
        enqueueSuccessfulStreamLinkResponse()

        val streamLink = remoteDataSource.getStreamLink("media-id")

        assertThat(streamLink.toString()).isEqualTo("stream/link")
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
    fun whenStreamLinkRequestFails_shouldThrowException() {
        enqueueDropboxApiErrorResponse()

        runBlocking {
            remoteDataSource.getStreamLink("media-id")
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
        val entries = listOf<Media>(mockk(), mockk(), mockk())
        val mediaResponse = MediaResponse(entries)
        val json = Gson().toJson(mediaResponse)
        val response = MockResponse().setResponseCode(200).setBody(json)
        mockDropboxApi.enqueue(response)
    }

    private fun enqueueSuccessfulStreamLinkResponse() {
        val streamLink = "stream/link"
        val streamLinkResponse = StreamLinkResponse(streamLink)
        val json = Gson().toJson(streamLinkResponse)
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