package com.github.app.domain.ext

import com.github.app.domain.exception.DomainException
import java.io.IOException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ExceptionExtTest {

    @Test
    fun `verify a result failure with IO exception is mapped to domain NetworkException`() {
        val result = Result.failure<Any>(IOException())

        val actual = result.mapToDomainException()

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is DomainException.NetworkException)
    }

    @Test
    fun `verify a result failure with any exception is mapped to domain UnknownException`() {
        val result = Result.failure<Any>(Exception())

        val actual = result.mapToDomainException()

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is DomainException.UnknownException)
    }

    @Test
    fun `verify a result success is left untouched when mapping to domain exception`() {
        val result = Result.success(Unit)

        val actual = result.mapToDomainException()

        assertTrue(actual.isSuccess)
        assertEquals(result, actual)
    }
}