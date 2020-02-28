package com.ukdev.carcadasalborghetti.api.tools

import com.ukdev.carcadasalborghetti.model.GenericError
import com.ukdev.carcadasalborghetti.model.NetworkError
import com.ukdev.carcadasalborghetti.model.Result
import com.ukdev.carcadasalborghetti.model.Success
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class IOHelper(private val crashReportManager: CrashReportManager) {

    suspend fun <T> safeIOCall(
            mainCall: suspend () -> T,
            alternative: (suspend () -> T)
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Success(mainCall.invoke())
            } catch (t: Throwable) {
                crashReportManager.logException(t)
                val apiError = getError(t)
                tryRunAlternative(apiError, alternative)
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