package com.github.app.domain.ext

import com.github.app.domain.exception.DomainException

/**
 * Executes the given [block] and catches any exceptions, wrapping them in a [Result.failure]
 * with a [DomainException].
 * If the [block] executes successfully, its result is wrapped in a [Result.success].
 *
 * This function is useful for converting exceptions thrown by external libraries or network calls
 * into a domain-specific error that can be handled by the application.
 */
internal suspend fun <T> runRecovering(
    exceptionMessage: String? = null,
    block: suspend () -> T,
): Result<T> {
    return runCatching { block() }.mapToDomainException(exceptionMessage)
}
