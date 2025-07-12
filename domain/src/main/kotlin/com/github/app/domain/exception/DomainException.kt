package com.github.app.domain.exception

/**
 * Base class for domain-specific exceptions.
 *
 * @param cause The underlying cause of this exception, if any.
 */
sealed class DomainException(override val cause: Throwable?) : Exception(cause) {

    /**
     * Represents errors related to network operations.
     */
    class NetworkException(
        override val cause: Throwable?,
    ) : DomainException(cause)

    /**
     * A generic or unknown error that doesn't fit into more specific categories.
     */
    class UnknownException(
        override val cause: Throwable? = null,
    ) : DomainException(cause)
}
