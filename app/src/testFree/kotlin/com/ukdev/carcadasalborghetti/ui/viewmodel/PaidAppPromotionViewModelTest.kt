package com.ukdev.carcadasalborghetti.ui.viewmodel

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

private const val PAID_VERSION_DOWNLOAD_URL = "https://play.google.com/store/apps/details?id=com.ukdev.carcadasalborghetti.paid"

@OptIn(ExperimentalCoroutinesApi::class)
class PaidAppPromotionViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val viewModel = PaidAppPromotionViewModel(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Uri::parse)
    }

    @Test
    fun `onGetPaidVersionClicked should send OpenWebPage action`() = runTest(testDispatcher) {
        // GIVEN
        val uri = mockk<Uri>()
        every { Uri.parse(PAID_VERSION_DOWNLOAD_URL) } returns uri

        // WHEN
        viewModel.onGetPaidVersionClicked()

        // THEN
        val expected = PaidAppPromotionUiAction.OpenWebPage(uri)
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }
}
