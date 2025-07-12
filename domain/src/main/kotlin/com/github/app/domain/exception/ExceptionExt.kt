package com.github.app.domain.exception

import timber.log.Timber
import java.io.IOException

internal fun <T> Result<T>.recoverWithDomainException(
    message: String? = null,
) = recoverCatching { throwable ->

    if (message != null) {
        Timber.e(throwable, message)
    }

    throw when (throwable) {
        is IOException -> DomainException.NetworkException(throwable)
        else -> DomainException.UnknownException(throwable)
    }
}
