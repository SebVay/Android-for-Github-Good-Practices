@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.app.ui.ext

import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlin.time.Duration

/**
 * Extension function for testing [StateFlow] instances using Turbine.
 * This function simplifies the process of testing StateFlow emissions by providing
 * options to advance the test coroutine scheduler, skip initial items,
 * and ignore remaining items at the end of the test.
 *
 * It reduces overhead and allow to focus on the test content by improving testing readability.
 */
context(testScope: TestScope)
suspend fun <T> StateFlow<T>.test(
    advanceUntilIdle: Boolean = false,
    skipFirstItems: Boolean = false,
    ignoreRemaining: Boolean = false,
    turbineTimeout: Duration? = null,
    turbineName: String? = null,
    block: suspend TurbineTestContext<T>.() -> Unit,
) {
    test(
        timeout = turbineTimeout,
        name = turbineName
    ) {
        testScope.takeIf { advanceUntilIdle }?.advanceUntilIdle()

        if (skipFirstItems) expectMostRecentItem()

        block()

        if (ignoreRemaining) cancelAndIgnoreRemainingEvents()
    }
}

/**
 * Advances the coroutine scheduler until idle and skips a specified number of items from the Turbine flow.
 *
 * This function is useful when you want to ignore a certain number of emissions
 * after some asynchronous operations triggered by advancing the scheduler have completed.
 *
 * @param skip The number of items to skip from the Turbine flow after advancing the scheduler.
 */
context(testScope: TestScope)
suspend fun <T> TurbineTestContext<T>.advanceAndSkipBy(skip: Int) {
    testScope.advanceUntilIdle()
    skipItems(skip)
}

/**
 * Convenience alias for [app.cash.turbine.awaitItem]
 * @return The next item emitted by the flow.
 */
suspend inline fun <T> TurbineTestContext<T>.nextItem() = awaitItem()
