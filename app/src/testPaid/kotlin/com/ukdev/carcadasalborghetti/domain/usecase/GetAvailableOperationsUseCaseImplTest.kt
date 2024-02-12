package com.ukdev.carcadasalborghetti.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAvailableOperationsUseCaseImplTest {

    private val mockRepository = mockk<MediaRepository>()
    private val useCase = GetAvailableOperationsUseCaseImpl(mockRepository)

    @Test
    fun `invoke should include remove from favourites operation`() = runTest {
        // GIVEN
        val media = stubMedia()
        coEvery { mockRepository.isSavedToFavourites(media) } returns true

        // WHEN
        val flow = useCase(media)

        // THEN
        val expected = listOf(Operation.SHARE, Operation.REMOVE_FROM_FAVOURITES)
        flow.test {
            assertThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should include add to favourites operation`() = runTest {
        // GIVEN
        val media = stubMedia()
        coEvery { mockRepository.isSavedToFavourites(media) } returns false

        // WHEN
        val flow = useCase(media)

        // THEN
        val expected = listOf(Operation.SHARE, Operation.ADD_TO_FAVOURITES)
        flow.test {
            assertThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }
}
