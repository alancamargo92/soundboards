package com.ukdev.carcadasalborghetti.core.tools

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val FILE_NAME = "preferences"
private const val KEY_SHOW_TIP = "show_tip"

class PreferencesManagerImplTest {

    private val mockSharedPreferences = mockk<SharedPreferences>(relaxed = true)
    private val mockContext = mockk<Context> {
        every {
            getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        } returns mockSharedPreferences
    }
    private val preferencesManager = PreferencesManagerImpl(mockContext)

    @Test
    fun `shouldShowTip should return false`() {
        // GIVEN
        every { mockSharedPreferences.getBoolean(KEY_SHOW_TIP, true) } returns false

        // WHEN
        val actual = preferencesManager.shouldShowTip()

        // THEN
        assertThat(actual).isFalse()
    }

    @Test
    fun `disableTip should change value in shared preferences`() {
        // WHEN
        preferencesManager.disableTip()

        // THEN
        verify {
            mockSharedPreferences.edit { putBoolean(KEY_SHOW_TIP, false) }
        }
    }
}
