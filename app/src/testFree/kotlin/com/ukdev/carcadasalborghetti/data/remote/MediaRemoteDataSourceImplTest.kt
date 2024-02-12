package com.ukdev.carcadasalborghetti.data.remote

import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.testtools.stubFile
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaRemoteDataSourceImplTest {

    private val remoteDataSource = MediaRemoteDataSourceImpl()

    @Test(expected = IllegalStateException::class)
    fun `getMediaList should throw exception`() = runTest {
        remoteDataSource.getMediaList(MediaType.AUDIO)
    }

    @Test(expected = IllegalStateException::class)
    fun `download should throw exception`() = runTest {
        // GIVEN
        val media = stubMedia()
        val destinationFile = stubFile()

        // WHEN
        remoteDataSource.download(media, destinationFile)
    }
}
