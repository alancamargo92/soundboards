package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.data.db.FavouritesDao
import com.ukdev.carcadasalborghetti.data.mapping.toDb
import com.ukdev.carcadasalborghetti.data.mapping.toDomain
import com.ukdev.carcadasalborghetti.domain.cache.CacheManager
import com.ukdev.carcadasalborghetti.testtools.stubDbMediaList
import com.ukdev.carcadasalborghetti.testtools.stubFile
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File

class MediaLocalDataSourceImplTest {

    private val mockDao = mockk<FavouritesDao>(relaxed = true)
    private val mockCacheManager = mockk<CacheManager>(relaxed = true)
    private val mockContext = mockk<Context>()

    private val localDataSource = MediaLocalDataSourceImpl(
        mockDao,
        mockCacheManager,
        mockContext
    )

    @Test
    fun `getFavourites should get favourites`() = runTest {
        // GIVEN
        val dbMediaList = stubDbMediaList()
        every { mockDao.getFavourites() } returns flowOf(dbMediaList)

        // WHEN
        val flow = localDataSource.getFavourites()

        // THEN
        val expected = dbMediaList.map { it.toDomain() }
        flow.test {
            assertThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `saveToFavourites should save media to favourites`() = runTest {
        // GIVEN
        val media = stubMedia()

        // WHEN
        localDataSource.saveToFavourites(media)

        // THEN
        val expected = media.toDb()
        coVerify { mockDao.insert(expected) }
    }

    @Test
    fun `removeFromFavourites should save media to favourites`() = runTest {
        // GIVEN
        val media = stubMedia()

        // WHEN
        localDataSource.removeFromFavourites(media)

        // THEN
        val expected = media.toDb()
        coVerify { mockDao.delete(expected) }
    }

    @Test
    fun `isSavedToFavourites should return true`() = runTest {
        // GIVEN
        val media = stubMedia()
        coEvery { mockDao.count(media.id) } returns 1

        // WHEN
        val actual = localDataSource.isSavedToFavourites(media)

        // THEN
        assertThat(actual).isTrue()
    }

    @Test
    fun `isSavedToFavourites should return false`() = runTest {
        // GIVEN
        val media = stubMedia()
        coEvery { mockDao.count(media.id) } returns 0

        // WHEN
        val actual = localDataSource.isSavedToFavourites(media)

        // THEN
        assertThat(actual).isFalse()
    }

    @Test
    fun `createFile should create local file`() {
        // GIVEN
        val media = stubMedia()
        val dir = stubFile()
        every { mockCacheManager.getDir(media.type) } returns dir

        // WHEN
        val actual = localDataSource.createFile(media)

        // THEN
        val expected = File("$dir/${media.title}")
        assertThat(actual).isEqualTo(expected)
    }
}
