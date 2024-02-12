package com.ukdev.carcadasalborghetti.domain.usecase

import app.cash.turbine.test
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveToFavouritesUseCaseImplTest {

    private val mockRepository = mockk<MediaRepository>(relaxed = true)
    private val useCase = SaveToFavouritesUseCaseImpl(mockRepository)

    @Test
    fun `invoke should save media to favourites`() = runTest {
        // GIVEN
        val media = stubMedia()

        // WHEN
        val flow = useCase(media)

        // THEN
        flow.test {
            awaitItem()
            awaitComplete()
        }

        coVerify { mockRepository.saveToFavourites(media) }
    }
}
