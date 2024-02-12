package com.ukdev.carcadasalborghetti.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DownloadMediaUseCaseImplTest {

    private val mockRepository = mockk<MediaRepository>()
    private val useCase = DownloadMediaUseCaseImpl(mockRepository)

    @Test
    fun `invoke should download media`() = runTest {
        // GIVEN
        val media = stubMedia()
        val expected = stubMedia()
        coEvery { mockRepository.downloadMedia(media) } returns expected

        // WHEN
        val flow = useCase(media)

        // THEN
        flow.test {
            assertThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }
}
