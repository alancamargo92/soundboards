package com.ukdev.carcadasalborghetti.core.tools

import android.util.Log
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val TAG = "LOG_CARCADAS"

class LoggerImplTest {

    private val logger = LoggerImpl()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `debug should log debug message`() {
        // GIVEN
        val message = "message"

        // WHEN
        logger.debug(message)

        // THEN
        verify { Log.d(TAG, message) }
    }

    @Test
    fun `error should log exception`() {
        // GIVEN
        val exception = IllegalStateException()

        // WHEN
        logger.error(exception)

        // THEN
        verify { Log.e(TAG, exception.message, exception) }
    }
}
