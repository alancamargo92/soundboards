package com.ukdev.carcadasalborghetti.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import com.ukdev.carcadasalborghetti.testtools.stubMediaList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMediaListUseCaseImplTest {

    private val mockRepository = mockk<MediaRepository>()
    private val useCase = GetMediaListUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get media list`() = runTest {
        // GIVEN
        val expected = stubMediaList()
        coEvery { mockRepository.getMediaList(MediaType.AUDIO) } returns expected

        // WHEN
        val flow = useCase(MediaType.AUDIO)

        // THEN
        flow.test {
            assertThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }
}
