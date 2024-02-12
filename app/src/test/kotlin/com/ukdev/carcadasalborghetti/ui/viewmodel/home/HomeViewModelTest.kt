package com.ukdev.carcadasalborghetti.ui.viewmodel.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.core.tools.PreferencesManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val mockPreferencesManager = mockk<PreferencesManager>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private val viewModel = HomeViewModel(mockPreferencesManager, testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `start should send ShowTip action`() = runTest(testDispatcher) {
        // GIVEN
        every { mockPreferencesManager.shouldShowTip() } returns true

        // WHEN
        viewModel.start()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(HomeUiAction.ShowTip)
        }
    }

    @Test
    fun `onDoNotShowTipAgainClicked should disable tip`() {
        // WHEN
        viewModel.onDoNotShowTipAgainClicked()

        // THEN
        verify { mockPreferencesManager.disableTip() }
    }
}
