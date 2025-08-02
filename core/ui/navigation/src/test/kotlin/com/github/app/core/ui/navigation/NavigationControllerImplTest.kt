package com.github.app.core.ui.navigation

import androidx.compose.runtime.Composable
import app.cash.turbine.test
import com.github.app.core.ui.navigation.screen.Screen
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NavigationControllerImplTest {

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify navigation event with a new route is emitted`() = runTest {
        val navigationController = NavigationControllerImpl()

        navigationController.events.test {
            navigationController.navigateTo(FakeScreen)

            awaitItem() shouldBeEqual NavigationCommand.Navigate("fake_route")
        }
    }

    @Test
    fun `verify navigate back event is emitted`() = runTest {
        val navigationController = NavigationControllerImpl()

        navigationController.events.test {
            navigationController.navigateBack()

            awaitItem() shouldBeEqual NavigationCommand.Back
        }
    }
}

private object FakeScreen : Screen("fake_route") {
    @Composable
    override fun Content() = Unit
}
