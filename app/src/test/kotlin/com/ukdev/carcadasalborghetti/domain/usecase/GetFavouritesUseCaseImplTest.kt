package com.ukdev.carcadasalborghetti.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import com.ukdev.carcadasalborghetti.testtools.stubMediaList
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetFavouritesUseCaseImplTest {

    private val mockRepository = mockk<MediaRepository>()
    private val useCase = GetFavouritesUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get favourites`() {
        // GIVEN
        val mediaList = stubMediaList()
        val expected = flowOf(mediaList)
        every { mockRepository.getFavourites() } returns expected

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isEqualTo(expected)
    }
}
