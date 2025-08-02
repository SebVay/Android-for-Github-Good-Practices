package com.github.app.core.viewmodel

import com.github.app.ui.ext.nextItem
import com.github.app.ui.ext.test
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify collectCatching collect and the result`() = runTest {
        val fakeDomainDependency = mockk<FakeDomainDependency>()
        val viewModel = FakeAppViewModel(fakeDomainDependency)
        val givenSuccess = Result.success("success")

        coEvery { fakeDomainDependency.getThings() } returns givenSuccess

        viewModel.testCollectCatching()

        viewModel.viewStateFlow.test(
            advanceUntilIdle = true,
            skipFirstItems = true,
        ) {
            viewModel.collectCatchingResult shouldBe givenSuccess
        }
    }

    @Test
    fun `verify collectCatching emit a Result failure if an exception occurs`() = runTest {
        val fakeDomainDependency = mockk<FakeDomainDependency>()
        val viewModel = FakeAppViewModel(fakeDomainDependency)
        val givenException = IllegalArgumentException()

        coEvery { fakeDomainDependency.getThings() } throws givenException

        viewModel.testCollectCatching()

        viewModel.viewStateFlow.test(
            advanceUntilIdle = true,
            skipFirstItems = true,
        ) {
            viewModel.collectCatchingResult shouldBe Result.failure(givenException)
        }
    }

    @Test
    fun `verify updateViewState update the stateFlow accordingly`() = runTest {
        val viewModel = FakeAppViewModel(mockk())

        viewModel.viewStateFlow.test {
            nextItem().counter shouldBe 0

            viewModel.testUpdateViewState()

            nextItem().counter shouldBe 1

            viewModel.testUpdateViewState()
            viewModel.testUpdateViewState()

            nextItem().counter shouldBe 2
            nextItem().counter shouldBe 3

            ensureAllEventsConsumed()
        }
    }
}

private data class FakeViewState(
    val counter: Int = 0,
) : AppViewState

private class FakeAppViewModel(
    private val fakeDomainDependency: FakeDomainDependency,
) : AppViewModel<FakeViewState>(FakeViewState()) {

    var collectCatchingResult: Result<String>? = null

    fun testCollectCatching() {
        viewStateFlow
            .map { fakeDomainDependency.getThings() }
            .collectCatching { result ->
                collectCatchingResult = result
            }
    }

    fun testUpdateViewState() {
        updateViewState {
            copy(counter = counter.inc())
        }
    }
}

private interface FakeDomainDependency {
    suspend fun getThings(): Result<String>
}
