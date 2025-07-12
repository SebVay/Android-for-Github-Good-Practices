package com.github.app.ui.repo.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
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
import com.github.app.core.ui.component.MultiDevicePreviews
import com.github.app.core.ui.component.loading.AppProgressIndicator
import com.github.app.core.ui.component.text.BATitleLarge
import com.github.app.core.ui.component.topappbar.BATopAppBar
import com.github.app.core.ui.navigation.screen.Screen
import com.github.app.core.ui.theme.GithubAppDimens
import com.github.app.core.ui.theme.GithubAppTheme
import com.github.app.core.viewmodel.UiState
import com.github.app.core.viewmodel.UiState.Loading
import com.github.app.ui.repo.R
import com.github.app.ui.repo.component.RepositoryCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Stable
object RepositoriesScreen : Screen(ROUTE) {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<RepositoriesScreenViewModel>()

        Content(
            viewModel.viewStateFlow.collectAsStateWithLifecycle(),
            viewModel::onClickRepository,
        )
    }
}

@Stable
object RepositoriesScreen2 : Screen(ROUTE + "2") {

    @Composable
    override fun Content() {
    }
}

@Composable
private fun Content(
    viewState: State<TrendingRepositoriesScreenViewState>,
    onClickRepository: (RepositoryViewState) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            BATopAppBar(
                title = stringResource(R.string.repository_list_title),
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { innerPadding ->
        AnimatedContent(
            modifier = Modifier.padding(innerPadding),
            targetState = viewState.value.repositoriesUiState,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
        ) { targetState ->
            when (targetState) {
                is UiState.Success -> SuccessState(targetState, onClickRepository)

                UiState.Error -> ErrorState()

                Loading -> LoadingState()

                UiState.Empty -> EmptyState()
            }
        }
    }
}

@Composable
private fun LoadingState() {
    AppProgressIndicator(Modifier.fillMaxWidth())
}

@Composable
private fun SuccessState(
    targetState: UiState.Success<ImmutableList<RepositoryViewState>>,
    onClickRepository: (RepositoryViewState) -> Unit,
) {
    RepositoryList(
        targetState.data,
        onClickRepository,
    )
}

@Composable
private fun EmptyState() {
    BATitleLarge("TODO : Empty Content")
}

@Composable
private fun ErrorState() {
    BATitleLarge("TODO : An error occurred")
}

@Composable
private fun RepositoryList(
    repositories: ImmutableList<RepositoryViewState>,
    onClickRepository: (RepositoryViewState) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 360.dp),
            contentPadding = PaddingValues(GithubAppDimens.Padding.DOUBLE),
            horizontalArrangement = Arrangement.spacedBy(GithubAppDimens.Padding.UNIT),
            verticalArrangement = Arrangement.spacedBy(GithubAppDimens.Padding.DOUBLE),
        ) {
            items(repositories) { repository ->
                RepositoryCard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxSize(),
                    onClickRepository = { onClickRepository(repository) },
                    repository = repository,
                )
            }
        }
    }
}

@MultiDevicePreviews
@Composable
internal fun RepositoriesScreenPreviewLoadingState() {
    GithubAppTheme {
        Content(
            viewState = rememberUpdatedState(
                TrendingRepositoriesScreenViewState(Loading),
            ),
            onClickRepository = {
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
                TrendingRepositoriesScreenViewState(
                    UiState.Success(
                        persistentListOf(
                            RepositoryViewState(
                                repositoryId = "1",
                                repositoryName = "Android Architecture Components",
                                imageUrl = "https://example.com/image1.jpg",
                                description = "A collection of libraries that help you design " +
                                    "robust, testable, and maintainable apps.",
                                stargazerCount = 1250,
                                forkCount = 340,
                                issuesCount = 45,
                                pullRequestsCount = 23,
                                authorName = "author",
                            ),
                            RepositoryViewState(
                                repositoryId = "2",
                                repositoryName = "Jetpack Compose",
                                imageUrl = "https://example.com/image2.jpg",
                                description = "Android's modern toolkit for building native UI.",
                                stargazerCount = 2890,
                                forkCount = 567,
                                issuesCount = 123,
                                pullRequestsCount = 78,
                                authorName = "author",
                            ),
                            RepositoryViewState(
                                repositoryId = "3",
                                repositoryName = "Kotlin Coroutines",
                                imageUrl = "https://example.com/image3.jpg",
                                description = "Library support for Kotlin coroutines with multiplatform support.",
                                stargazerCount = 4567,
                                forkCount = 890,
                                issuesCount = 67,
                                pullRequestsCount = 34,
                                authorName = "author",
                            ),
                        ),
                    ),
                ),
            ),
            onClickRepository = {
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
                TrendingRepositoriesScreenViewState(UiState.Error),
            ),
            onClickRepository = {
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
                TrendingRepositoriesScreenViewState(UiState.Empty),
            ),
            onClickRepository = {
                // Empty
            },
        )
    }
}

private const val ROUTE = "home_screen"
