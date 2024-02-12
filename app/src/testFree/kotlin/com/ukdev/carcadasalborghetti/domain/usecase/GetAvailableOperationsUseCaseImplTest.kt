package com.ukdev.carcadasalborghetti.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAvailableOperationsUseCaseImplTest {

    private val useCase = GetAvailableOperationsUseCaseImpl()

    @Test
    fun `invoke should return only share option`() = runTest {
        // GIVEN
        val media = stubMedia()

        // WHEN
        val flow = useCase(media)

        // THEN
        val expected = listOf(Operation.SHARE)
        flow.test {
            assertThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }
}
