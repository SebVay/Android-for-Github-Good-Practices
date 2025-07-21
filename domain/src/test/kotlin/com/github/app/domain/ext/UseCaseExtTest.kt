package com.github.app.domain.ext

import com.github.app.domain.exception.DomainException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.IOException

class UseCaseExtTest {
    @Test
    fun `verify an IO exception is converted to a Result failure with NetworkException and message is preserved`() = runTest{
        val actual = runRecovering {
            throw IOException()
        }

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is DomainException.NetworkException)
    }

    @Test
    fun `verify a result failure with any exception is mapped to domain UnknownException and message is preserved`() = runTest{
        val actual = runRecovering {
            throw Exception()
        }

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is DomainException.UnknownException)
    }

    @Test
    fun `verify a result success is left untouched when mapping to domain exception`() = runTest{
        val givenMessage = "everything is awesome"

        val actual = runRecovering {
            givenMessage
        }

        assertTrue(actual.isSuccess)
        assertEquals(Result.success(givenMessage), actual)
    }
}