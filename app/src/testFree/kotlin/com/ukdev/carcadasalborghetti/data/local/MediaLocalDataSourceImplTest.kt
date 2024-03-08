package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaLocalDataSourceImplTest {

    private val mockContext = mockk<Context>()
    private val localDataSource = MediaLocalDataSourceImpl(mockContext)

    @Test
    fun `getFavourites should return empty flow`() = runTest {
        // WHEN
        val flow = localDataSource.getFavourites()

        // THEN
        flow.test {
            awaitComplete()
        }
    }

    @Test
    fun `isSavedToFavourites should return false`() = runTest {
        // GIVEN
        val media = stubMedia()

        // WHEN
        val actual = localDataSource.isSavedToFavourites(media)

        // THEN
        assertThat(actual).isFalse()
    }
}
