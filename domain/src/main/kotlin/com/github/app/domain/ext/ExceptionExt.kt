package com.github.app.domain.ext

import com.github.app.domain.exception.DomainException
import timber.log.Timber
import java.io.IOException

/**
 * Recovers from a [Result] that failed by rethrowing a [com.github.app.domain.exception.DomainException].
 *
 * This function is useful for converting generic exceptions into domain-specific exceptions,
 * providing more context and control over error handling (ie. digested by the UI layer).
 */
internal fun <T> Result<T>.mapToDomainException(message: String? = null): Result<T> {
    return recoverCatching { throwable ->

        if (message != null) {
            Timber.e(throwable, message)
        }

        throw when (throwable) {
            is IOException -> DomainException.NetworkException(throwable)
            else -> DomainException.UnknownException(throwable)
        }
    }
}
