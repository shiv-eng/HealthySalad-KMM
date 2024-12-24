package navigation

import androidx.compose.runtime.Composable
import di.component.AppComponent
import ui.screen.detail.PostDetailScreen
import ui.screen.home.HomeScreen
import utils.navigation.NavHost
import utils.navigation.OnState
import utils.navigation.navStackViewModel
import utils.navigation.rememberNavigationController

@Composable
fun NavGraph() {
    val navController = rememberScreenNavController()

    NavHost(navController, initialState = Screen.Home) {
        OnState<Screen.Home> {
            val viewModel = it.navStackViewModel {
                AppComponent.viewModelComponent.provideHomeViewModel(it)
            }

            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { postId -> navController.navigateTo(Screen.PostDetail(postId)) },
            )
        }

        OnState<Screen.PostDetail> {
            val postId = it.state.postId
            val viewModel = it.navStackViewModel(key = postId) {
                AppComponent.viewModelComponent.providePostDetailViewModel(it, postId)
            }

            PostDetailScreen(viewModel = viewModel, onNavigateUp = navController::navigateUp)
        }
    }
}

@Composable
fun rememberScreenNavController() = rememberNavigationController<Screen, Map<String, String>>(
    onSave = { screen -> screen.asSavable() },
    onRestore = { savable -> buildScreenFromSavable(savable) },
)
