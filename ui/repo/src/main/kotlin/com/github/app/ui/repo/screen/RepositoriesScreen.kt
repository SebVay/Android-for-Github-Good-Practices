package com.github.app.ui.repo.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.github.app.core.ui.component.MultiDevicePreviews
import com.github.app.core.ui.component.loading.AppProgressIndicator
import com.github.app.core.ui.component.text.AppTitleLarge
import com.github.app.core.ui.component.topappbar.AppTopAppBar
import com.github.app.core.ui.navigation.screen.Screen
import com.github.app.core.ui.theme.GithubAppDimens
import com.github.app.core.ui.theme.GithubAppTheme
import com.github.app.core.viewmodel.UiState
import com.github.app.core.viewmodel.UiState.Loading
import com.github.app.ui.repo.R
import com.github.app.ui.repo.component.button.RepositoryFilterButtons
import com.github.app.ui.repo.component.card.RepositoryCard
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@Stable
object RepositoriesScreen : Screen(ROUTE) {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<RepositoriesScreenViewModel>()

        Content(
            viewModel.viewStateFlow.collectAsStateWithLifecycle(),
            viewModel::onClickRepository,
            viewModel::onClickFilterButton,
        )
    }
}

// Going to be removed
@Stable
object RepositoriesScreen2 : Screen(ROUTE + "2") {

    @Composable
    override fun Content() {
        // Empty
    }
}

@Composable
private fun Content(
    viewState: State<TrendingRepositoriesScreenViewState>,
    onClickRepository: (RepositoryViewState) -> Unit,
    onFilterButtonClick: (FilterButtonViewState) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppTopAppBar(
                title = stringResource(R.string.repository_list_title),
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { innerPadding ->
        AnimatedContent(
            modifier = Modifier.padding(innerPadding),
            targetState = viewState.value.currentState,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
        ) { targetState ->
            when (targetState) {
                is UiState.Success -> {
                    // Nothing
                }

                UiState.Error -> ErrorState()
                Loading -> LoadingState()
                UiState.Empty -> EmptyState()
            }
        }

        val repositories = viewState.value.repositories

        RepositoriesContent(
            modifier = Modifier
                .padding(innerPadding),
            repositories = repositories,
            viewState = viewState,
            onFilterButtonClick = onFilterButtonClick,
            onClickRepository = onClickRepository,
        )
    }
}

@Composable
private fun RepositoriesContent(
    repositories: ImmutableList<RepositoryViewState>,
    viewState: State<TrendingRepositoriesScreenViewState>,
    onFilterButtonClick: (FilterButtonViewState) -> Unit,
    modifier: Modifier = Modifier,
    onClickRepository: (RepositoryViewState) -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = repositories.isNotEmpty(),
        enter = fadeIn(tween()) + scaleIn(tween(), INITIAL_SCALE_ANIM),
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 360.dp),
            contentPadding = PaddingValues(GithubAppDimens.Padding.DOUBLE),
            horizontalArrangement = Arrangement.spacedBy(GithubAppDimens.Padding.UNIT),
            verticalArrangement = Arrangement.spacedBy(GithubAppDimens.Padding.DOUBLE),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AppTitleLarge(stringResource(R.string.trending_repositories))

                    RepositoryFilterButtons(
                        filterButtons = viewState.value.filterButtons,
                        onFilterButtonClick = onFilterButtonClick,
                    )
                }
            }

            items(items = repositories) { repository ->
                RepositoryCard(
                    modifier = Modifier
                        .animateItem(
                            placementSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                            ),
                        )
                        .fillMaxSize(),
                    onClickRepository = dropUnlessResumed { onClickRepository(repository) },
                    repository = repository,
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    AppProgressIndicator(Modifier.fillMaxWidth())
}

@Composable
private fun EmptyState() {
    AppTitleLarge("TODO : Empty Content")
}

@Composable
private fun ErrorState() {
    AppTitleLarge("TODO : An error occurred")
}

@MultiDevicePreviews
@Composable
internal fun RepositoriesScreenPreviewLoadingState() {
    GithubAppTheme {
        Content(
            viewState = rememberUpdatedState(
                TrendingRepositoriesScreenViewState.initialState().withRequestLoading(),
            ),
            onClickRepository = {
                // Empty
            },
            onFilterButtonClick = {
                // Empty
            },
        )
    }
}

@MultiDevicePreviews
@Composable
internal fun RepositoriesScreenPreviewSuccessState() {
    GithubAppTheme {
        Content(
            viewState = rememberUpdatedState(
                TrendingRepositoriesScreenViewState
                    .initialState()
                    .withRepositories(
                        listOf(
                            RepositoryViewState(
                                id = "1",
                                name = "Android Architecture Components",
                                imageUrl = "https://example.com/image1.jpg",
                                description = "A collection of libraries that help you design " +
                                    "robust, testable, and maintainable apps.",
                                stargazerCount = 1250,
                                forkCount = 340,
                                issuesCount = 45,
                                pullRequestsCount = 23,
                                authorName = "author",
                                discussionsCount = 12
                            ),
                            RepositoryViewState(
                                id = "2",
                                name = "Jetpack Compose",
                                imageUrl = "https://example.com/image2.jpg",
                                description = "Android's modern toolkit for building native UI.",
                                stargazerCount = 2890,
                                forkCount = 567,
                                issuesCount = 123,
                                pullRequestsCount = 78,
                                authorName = "author",
                                discussionsCount = 12,
                            ),
                            RepositoryViewState(
                                id = "3",
                                name = "Kotlin Coroutines",
                                imageUrl = "https://example.com/image3.jpg",
                                description = "Library support for Kotlin coroutines with multiplatform support.",
                                stargazerCount = 4567,
                                forkCount = 890,
                                issuesCount = 67,
                                pullRequestsCount = 34,
                                authorName = "author",
                                discussionsCount = 12
                            ),
                        ),
                    ),
            ),
            onClickRepository = {
                // Empty
            },
            onFilterButtonClick = {
                // Empty
            },
        )
    }
}

@MultiDevicePreviews
@Composable
internal fun RepositoriesScreenPreviewErrorState() {
    GithubAppTheme {
        Content(
            viewState = rememberUpdatedState(
                TrendingRepositoriesScreenViewState.initialState().withError()
            ),
            onClickRepository = {
                // Empty
            },
            onFilterButtonClick = {
                // Empty
            },
        )
    }
}

@MultiDevicePreviews
@Composable
internal fun RepositoriesScreenPreviewEmptyState() {
    GithubAppTheme {
        Content(
            viewState = rememberUpdatedState(
                TrendingRepositoriesScreenViewState.initialState()
                    .copy(currentState = UiState.Empty)
            ),
            onClickRepository = {
                // Empty
            },
            onFilterButtonClick = {
                // Empty
            },
        )
    }
}

private const val ROUTE = "repositories_screen"
private const val INITIAL_SCALE_ANIM = 0.95f
