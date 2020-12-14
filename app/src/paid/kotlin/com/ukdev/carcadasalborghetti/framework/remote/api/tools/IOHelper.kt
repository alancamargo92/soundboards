package com.ukdev.carcadasalborghetti.framework.remote.api.tools

import com.ukdev.carcadasalborghetti.data.entities.GenericError
import com.ukdev.carcadasalborghetti.data.entities.NetworkError
import com.ukdev.carcadasalborghetti.data.entities.Result
import com.ukdev.carcadasalborghetti.data.entities.Success
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class IOHelper(private val crashReportManager: CrashReportManager) {

    suspend fun <T> safeIOCall(block: suspend() -> T): Result<T> {
        return safeIOCall(block, null)
    }

    suspend fun <T> safeIOCall(
            mainCall: suspend () -> T,
            alternative: (suspend () -> T)? = null
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Success(mainCall.invoke())
            } catch (t: Throwable) {
                crashReportManager.logException(t)
                val originalError = getError(t)

                if (alternative != null)
                    tryRunAlternative(originalError, alternative)
                else
                    originalError
            }
        }
    }

    private fun getError(t: Throwable): Result<Nothing> {
        return when (t) {
            is IOException -> NetworkError
            else -> GenericError
        }
    }

    private suspend fun <T> tryRunAlternative(
            originalError: Result<Nothing>,
            block: suspend () -> T
    ): Result<T> {
        return try {
            Success(block.invoke())
        } catch (t: Throwable) {
            crashReportManager.logException(t)
            originalError
        }
    }

}