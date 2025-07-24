package com.github.app.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import kotlin.reflect.KProperty

/**
 * Remembers and observes a specific value derived from an [AppViewState].
 *
 * You'll need to use this function to observe a specific piece of data within your [AppViewState]
 * and have your Composable recompose only when that specific data changes, rather than recomposing for every change
 * in the entire [AppViewState].
 *
 * It uses [derivedStateOf] to efficiently track changes to the selected value.
 *
 * @param VS The type of the [AppViewState].
 * @param Value The type of the value to be remembered and observed.
 * @param viewStateValue A lambda function that extracts the desired value from the [AppViewState].
 *                       This lambda should not contain Composable calls, hence the [DisallowComposableCalls]
 *                       annotation.
 * @return A [State] object that holds the derived value and will trigger recomposition when it changes.
 */
@Composable
inline fun <VS : AppViewState, Value> State<VS>.rememberStateValue(
    crossinline viewStateValue: @DisallowComposableCalls VS.() -> Value,
): State<Value> {
    return remember { derivedStateOf { value.viewStateValue() } }
}

/**
 * Enables property delegation for [AppViewState] using the `by` keyword.
 * Should be used in conjunction with the `rememberStateValue` function.
 *
 * Example:
 * ```kotlin
 * val aValue by viewState.rememberStateValue { aValue }
 * ```
 *
 * @param VS The type of the [AppViewState].
 * @param thisObj The object instance that owns the delegated property (can be null for top-level properties).
 * @param property The metadata of the delegated property.
 * @return The current value of the [AppViewState] held within the [State].
 */
@Suppress("NOTHING_TO_INLINE")
inline operator fun <VS : AppViewState> State<VS>.getValue(thisObj: Any?, property: KProperty<*>): VS = value
