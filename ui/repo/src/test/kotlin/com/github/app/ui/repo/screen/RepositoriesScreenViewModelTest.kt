package com.github.app.ui.repo.screen

import com.github.app.core.ui.navigation.NavigationController
import com.github.app.core.viewmodel.UiState
import com.github.app.core.viewmodel.UiState.Loading
import com.github.app.core.viewmodel.UiState.Success
import com.github.app.domain.repo.model.Repository
import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCase
import com.github.app.domain.repo.usecase.TimePeriod
import com.github.app.ui.ext.advanceAndSkipBy
import com.github.app.ui.ext.nextItem
import com.github.app.ui.ext.test
import com.github.app.ui.ext.viewState
import com.github.app.ui.repo.R
import com.github.app.ui.repo.mapper.RepositoryViewStateMapper
import com.github.app.ui.repo.screen.placeholder.ScreenPlaceholder
import com.github.app.ui.repo.screen.state.FilterButtonViewState
import com.github.app.ui.repo.screen.state.FilterType
import com.github.app.ui.repo.screen.state.RepositoriesScreenViewState
import com.github.app.ui.repo.screen.state.RepositoryViewState
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldMatchInOrder
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoriesScreenViewModelTest {

    private val getTrendingRepositories: GetTrendingRepositoriesUseCase = mockk()
    private val repositoryMapper: RepositoryViewStateMapper = mockk()
    private val navigationController: NavigationController = mockk(relaxUnitFun = true)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel(
        initialState: RepositoriesScreenViewState = RepositoriesScreenViewState.initialState(),
    ): RepositoriesScreenViewModel {
        return RepositoriesScreenViewModel(
            initialState = initialState,
            navigationController = navigationController,
            getTrendingRepositories = getTrendingRepositories,
            repositoryMapper = repositoryMapper,
        )
    }

    @Test
    fun `verify the initial ui state`() {
        val viewModel = initViewModel()
        val currentViewState = viewModel.viewStateFlow.value

        currentViewState shouldBeEqual RepositoriesScreenViewState(
            currentState = Loading,
            repositories = persistentListOf(),
            filterButtons = persistentListOf(
                FilterButtonViewState(
                    filterType = FilterType.YESTERDAY,
                    textRes = R.string.filter_button_last_yesterday,
                    isSelected = true,
                ),
                FilterButtonViewState(
                    filterType = FilterType.LAST_WEEK,
                    textRes = R.string.filter_button_last_week,
                    isSelected = false,
                ),
                FilterButtonViewState(
                    filterType = FilterType.LAST_MONTH,
                    textRes = R.string.filter_button_last_month,
                    isSelected = false,
                ),
            ),
        )
    }

    @Test
    fun `verify the ui state is a success when repositories request succeed`() = runTest {
        // Given
        val domainRepository = mockk<Repository>()
        val repository = mockk<RepositoryViewState>()

        coEvery { getTrendingRepositories(TimePeriod.YESTERDAY) } returns Result.success(listOf(domainRepository))
        every { repositoryMapper(domainRepository) } returns repository

        // When
        val viewModel = initViewModel()

        // Then
        viewModel.viewStateFlow.test {
            nextItem().currentState shouldBeEqual Loading

            nextItem() should {
                it.currentState shouldBeEqual Success(Unit)
                it.repositories shouldBeEqual listOf(repository)
            }
        }
    }

    @Test
    fun `verify the ui state is an error when repositories request is a failure`() = runTest {
        // Given
        val domainRepository = mockk<Repository>()
        val repository = mockk<RepositoryViewState>()

        coEvery { getTrendingRepositories(TimePeriod.YESTERDAY) } returns Result.failure(IllegalArgumentException())
        every { repositoryMapper(domainRepository) } returns repository

        // When
        val viewModel = initViewModel()

        // Then
        viewModel.viewStateFlow.test {
            nextItem().currentState shouldBeEqual Loading

            nextItem() should {
                it.currentState shouldBeEqual UiState.Error
                it.repositories shouldBeEqual emptyList()
            }
        }
    }

    @Test
    fun `verify that when a filter button is clicked, it is then selected`() {
        // Given
        val filter1 = FilterButtonViewState(FilterType.YESTERDAY, 0, true)
        val filter2 = FilterButtonViewState(FilterType.LAST_WEEK, 1, false)

        val initialState = RepositoriesScreenViewState.initialState()
            .copy(filterButtons = persistentListOf(filter1, filter2))

        coEvery { getTrendingRepositories(TimePeriod.LAST_WEEK) } returns Result.success(emptyList())

        val viewModel = initViewModel(initialState)

        // When
        viewModel.onClickFilterButton(filter2)

        // Then
        viewModel.viewState().filterButtons.shouldMatchInOrder(
            { it.isSelected.shouldBeFalse() },
            { it.isSelected.shouldBeTrue() },
        )
    }

    @Test
    fun `verify that when a filter is clicked the repositories loads`() = runTest {
        val domainRepository = mockk<Repository>()
        val repository = mockk<RepositoryViewState>()

        val filter1 = FilterButtonViewState(FilterType.YESTERDAY, 0, true)
        val filter2 = FilterButtonViewState(FilterType.LAST_WEEK, 1, false)

        val initialState = RepositoriesScreenViewState.initialState()
            .copy(filterButtons = persistentListOf(filter1, filter2))

        coEvery { getTrendingRepositories(any()) } returns Result.success(listOf(domainRepository))
        every { repositoryMapper(domainRepository) } returns repository

        val viewModel = initViewModel(initialState)

        viewModel.viewStateFlow.test(
            advanceUntilIdle = true,
            skipFirstItems = true,
        ) {
            viewModel.onClickFilterButton(filter2)

            advanceAndSkipBy(1)

            nextItem().currentState shouldBe Loading
            nextItem().repositories shouldBeEqual listOf(repository)
        }
    }

    @Test
    fun `when a filter is clicked and repositories fails, old repositories are still shown`() = runTest {
        val domainRepository = mockk<Repository>()
        val repository = mockk<RepositoryViewState>()

        val filter1 = FilterButtonViewState(FilterType.YESTERDAY, 0, true)
        val filter2 = FilterButtonViewState(FilterType.LAST_WEEK, 1, false)

        val initialState = RepositoriesScreenViewState.initialState()
            .copy(filterButtons = persistentListOf(filter1, filter2))

        coEvery { getTrendingRepositories(TimePeriod.YESTERDAY) } returns Result.success(listOf(domainRepository))
        coEvery { getTrendingRepositories(TimePeriod.LAST_WEEK) } returns Result.failure(IllegalArgumentException())
        every { repositoryMapper(domainRepository) } returns repository

        val viewModel = initViewModel(initialState)

        viewModel.viewStateFlow.test(
            advanceUntilIdle = true,
            skipFirstItems = true,
        ) {
            viewModel.onClickFilterButton(filter2)

            advanceAndSkipBy(1)

            nextItem().currentState shouldBe Loading
            nextItem() should {
                it.currentState shouldBeEqual UiState.Error
                it.repositories shouldBeEqual listOf(repository)
            }
        }
    }

    @Test
    fun `when the same filter is applied, nothing happens`() = runTest {
        val domainRepository = mockk<Repository>()
        val repository = mockk<RepositoryViewState>()

        val sameFilter = FilterButtonViewState(FilterType.YESTERDAY, 0, true)

        val initialState = RepositoriesScreenViewState.initialState()
            .copy(filterButtons = persistentListOf(sameFilter))

        coEvery { getTrendingRepositories(TimePeriod.YESTERDAY) } returns Result.success(listOf(domainRepository))
        every { repositoryMapper(domainRepository) } returns repository

        val viewModel = initViewModel(initialState)

        viewModel.viewStateFlow.test(
            advanceUntilIdle = true,
            skipFirstItems = true
        ) {
            viewModel.onClickFilterButton(sameFilter)

            advanceUntilIdle()
            expectNoEvents()
        }
    }

    @Test
    fun `when repository is clicked, navigation is triggered`() {
        val viewModel = initViewModel()

        viewModel.onClickRepository(mockk())

        verify { navigationController.navigateTo(ScreenPlaceholder) }
    }
}
