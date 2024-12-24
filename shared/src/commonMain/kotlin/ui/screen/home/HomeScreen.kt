package ui.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.component.ErrorContent
import ui.component.PostCard
import ui.theme.LocalUiModePreferenceController
import ui.theme.UiMode
import ui.theme.rememberUiMode
import utils.navigation.rememberInNavStack

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    HomeContent(
        isLoading = state.isLoading,
        posts = state.posts,
        errorMessage = state.errorMessage,
        onNavigateToDetail = onNavigateToDetail,
        onRefresh = viewModel::refresh,
    )
}

@Composable
fun HomeContent(
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    errorMessage: String?,
    onNavigateToDetail: (Int) -> Unit,
    onRefresh: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeAppBar()

        Box(modifier = Modifier.fillMaxSize()) {
            if (errorMessage != null) {
                ErrorContent(errorMessage)
            } else {
                Crossfade(targetState = isLoading, animationSpec = tween(500)) { isLoadingState ->
                    PostListContent(
                        isLoading = isLoadingState,
                        posts = posts,
                        onNavigateToDetail = onNavigateToDetail,
                        onRefresh = onRefresh,
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Healthy Salads",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        backgroundColor = Color(0xFF2E7D32), // A nice green shade for health theme
        actions = {
            val controller = LocalUiModePreferenceController.current
            val uiMode by rememberUiMode()

            IconButton(onClick = controller::toggle) {
                Icon(
                    imageVector = when (uiMode) {
                        UiMode.DARK -> Icons.Outlined.WbSunny
                        UiMode.LIGHT -> Icons.Default.WbSunny
                    },
                    contentDescription = "Toggle UI mode",
                    tint = Color.White
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PostListContent(
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    onNavigateToDetail: (Int) -> Unit,
    onRefresh: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = onRefresh,
    )

    val listState = rememberInNavStack(
        key = "scrollState-$isLoading",
        compute = { LazyListState() },
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = if (isLoading) loadingPostCards else posts,
                key = { it.id },
            ) { post ->
                PostCard(
                    isLoading = isLoading,
                    post = post,
                    modifier = Modifier
                        .clickable(enabled = !isLoading) {
                            onNavigateToDetail(post.id)
                        }
                        .fillMaxWidth(),
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color(0xFF2E7D32),
            contentColor = Color.White,
        )
    }
}

private val loadingPostCards = List(6) { HomeState.Post(it, "", "") }
