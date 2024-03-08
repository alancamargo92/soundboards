package com.ukdev.carcadasalborghetti.data.repository

import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.core.tools.Logger
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.testtools.stubFile
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import com.ukdev.carcadasalborghetti.testtools.stubMediaList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File

class MediaRepositoryImplTest {

    private val mockRemoteDataSource = mockk<MediaRemoteDataSource>()
    private val mockLocalDataSource = mockk<MediaLocalDataSource>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)

    private val repository = MediaRepositoryImpl(
        mockRemoteDataSource,
        mockLocalDataSource,
        mockLogger
    )

    @Test
    fun `getMediaList should log fetching from remote`() = runTest {
        // GIVEN
        coEvery { mockRemoteDataSource.getMediaList(MediaType.AUDIO) } returns stubMediaList()

        // WHEN
        repository.getMediaList(MediaType.AUDIO)

        // THEN
        verify { mockLogger.debug(message = "Fetching from remote...") }
    }

    @Test
    fun `getMediaList should get media list from remote`() = runTest {
        // GIVEN
        val expected = stubMediaList()
        coEvery { mockRemoteDataSource.getMediaList(MediaType.AUDIO) } returns expected

        // WHEN
        val actual = repository.getMediaList(MediaType.AUDIO)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMediaList should log error`() = runTest {
        // GIVEN
        val exception = IllegalStateException()
        coEvery { mockRemoteDataSource.getMediaList(MediaType.AUDIO) } throws exception
        coEvery { mockLocalDataSource.getMediaList(MediaType.AUDIO) } returns stubMediaList()

        // WHEN
        repository.getMediaList(MediaType.AUDIO)

        // THEN
        verify { mockLogger.error(exception) }
    }

    @Test
    fun `getMediaList should log fetching from local`() = runTest {
        // GIVEN
        val exception = IllegalStateException()
        coEvery { mockRemoteDataSource.getMediaList(MediaType.AUDIO) } throws exception
        coEvery { mockLocalDataSource.getMediaList(MediaType.AUDIO) } returns stubMediaList()

        // WHEN
        repository.getMediaList(MediaType.AUDIO)

        // THEN
        verify { mockLogger.debug(message = "Fetching from local...") }
    }

    @Test
    fun `getMediaList should get media list from local`() = runTest {
        // GIVEN
        coEvery {
            mockRemoteDataSource.getMediaList(MediaType.AUDIO)
        } throws IllegalStateException()
        val expected = stubMediaList()
        coEvery { mockLocalDataSource.getMediaList(MediaType.AUDIO) } returns expected

        // WHEN
        val actual = repository.getMediaList(MediaType.AUDIO)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getFavourites should return favourites`() {
        // GIVEN
        val expected = flowOf(stubMediaList())
        every { mockLocalDataSource.getFavourites() } returns expected

        // WHEN
        val actual = repository.getFavourites()

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `saveToFavourites should save media to favourites`() = runTest {
        // GIVEN
        val media = stubMedia()

        // WHEN
        repository.saveToFavourites(media)

        // THEN
        coVerify { mockLocalDataSource.saveToFavourites(media) }
    }

    @Test
    fun `removeFromFavourites should remove media from favourites`() = runTest {
        // GIVEN
        val media = stubMedia()

        // WHEN
        repository.removeFromFavourites(media)

        // THEN
        coVerify { mockLocalDataSource.removeFromFavourites(media) }
    }

    @Test
    fun `isSavedToFavourites should return true`() = runTest {
        // GIVEN
        val media = stubMedia()
        coEvery { mockLocalDataSource.isSavedToFavourites(media) } returns true

        // WHEN
        val actual = repository.isSavedToFavourites(media)

        // THEN
        assertThat(actual).isTrue()
    }

    @Test
    fun `prepareMedia should set media id to downloaded media uri`() = runTest {
        // GIVEN
        val destinationFile = stubFile()
        val downloadedFile = stubFile()
        val uri = "uri"
        val media = stubMedia()
        every { mockLocalDataSource.createFile(media) } returns destinationFile
        coEvery {
            mockRemoteDataSource.download(media, destinationFile)
        } returns downloadedFile
        every { mockLocalDataSource.getFileUri(downloadedFile) } returns uri

        // WHEN
        val actual = repository.prepareMedia(media)

        // THEN
        val expected = media.copy(id = uri)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `prepareMedia should return locally prepared media`() = runTest {
        // GIVEN
        val media = stubMedia()
        val file = mockk<File>()
        every { mockLocalDataSource.createFile(media) } returns file
        coEvery {
            mockRemoteDataSource.download(media, destinationFile = any())
        } throws IllegalStateException()
        val uri = "uri"
        every { mockLocalDataSource.getFileUri(file) } returns uri

        // WHEN
        val actual = repository.prepareMedia(media)

        // THEN
        val expected = media.copy(id = uri)
        assertThat(actual).isEqualTo(expected)
    }
}
