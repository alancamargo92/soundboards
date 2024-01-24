package com.ukdev.carcadasalborghetti.framework.remote.api.tools

import com.ukdev.carcadasalborghetti.data.model.GenericError
import com.ukdev.carcadasalborghetti.data.model.NetworkError
import com.ukdev.carcadasalborghetti.data.model.Result
import com.ukdev.carcadasalborghetti.data.model.Success
import com.ukdev.carcadasalborghetti.data.tools.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class IOHelper @Inject constructor(private val logger: Logger) {

    suspend fun <T> safeIOCall(block: suspend () -> T): Result<T> {
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
                logger.logException(t)
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
            logger.logException(t)
            originalError
        }
    }
}
