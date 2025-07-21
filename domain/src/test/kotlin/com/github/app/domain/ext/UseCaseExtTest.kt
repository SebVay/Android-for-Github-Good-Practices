package com.github.app.domain.ext

import com.github.app.domain.exception.DomainException
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.IOException

class UseCaseExtTest {
    @Test
    fun `verify an IO exception is converted to a Result failure with NetworkException and message is preserved`() = runTest {
        val actual = runRecovering {
            throw IOException()
        }

        actual shouldBeFailure { throwable ->
            throwable.shouldBeTypeOf<DomainException.NetworkException>()
        }
    }

    @Test
    fun `verify a result failure with any exception is mapped to domain UnknownException and message is preserved`() = runTest {
        val actual = runRecovering {
            throw Exception()
        }

        actual shouldBeFailure { throwable ->
            throwable.shouldBeTypeOf<DomainException.UnknownException>()
        }
    }

    @Test
    fun `verify a result success is left untouched when mapping to domain exception`() = runTest {
        val givenMessage = "everything is awesome"

        val actual = runRecovering {
            givenMessage
        }

        actual shouldBeSuccess givenMessage
    }
}
