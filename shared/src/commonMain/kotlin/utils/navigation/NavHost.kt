
package utils.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import kotlinx.coroutines.flow.onSubscription
import utils.appfinisher.LocalAppFinisher
import utils.backhandler.BackHandler
import utils.navigation.impl.NavHostScopeImpl
import utils.navigation.impl.NavigationEvents
import utils.navigation.impl.rememberNavHostScope


@Composable
fun <STATE : Any> NavHost(
    navigationController: NavigationController<STATE>,
    initialState: STATE,
    block: NavHostScope<STATE>.() -> Unit,
) {
    val hostScope = rememberNavHostScope(navigationController, block)

    hostScope.NavHostImpl(
        navigationController = navigationController,
        initialState = initialState,
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun <STATE : Any> NavHostScopeImpl<STATE>.NavHostImpl(
    navigationController: NavigationController<STATE>,
    initialState: STATE,
) {
    val state by rememberNavState(
        navigationController = navigationController,
        initialState = initialState,
    )

    AnimatedContent(
        targetState = state,
        transitionSpec = {
            when (targetState?.animation) {
                NavigationAnimation.NAVIGATING -> {
                    slideInHorizontally { height -> height } + fadeIn() with
                        slideOutHorizontally { height -> -height } + fadeOut()
                }
                NavigationAnimation.POPPING_UP -> {
                    slideInHorizontally { height -> -height } + fadeIn() with
                        slideOutHorizontally { height -> height } + fadeOut()
                }
                else -> {
                    fadeIn() with fadeOut()
                }
            }.using(SizeTransform(clip = false))
        },
    ) { nextState ->
        val navState = nextState?.state
        if (navState != null) {
            this@NavHostImpl.compose(navState)
        }
    }

    BackHandler(true) { navigationController.navigateUp() }

    val appFinisher = LocalAppFinisher.current

    LaunchedEffect(state) {
        if (state?.animation === NavigationAnimation.EXIT) {
            appFinisher.finish()
        }
    }
}

@Composable
private fun <STATE : Any> rememberNavState(
    navigationController: NavigationController<STATE>,
    initialState: STATE,
): State<NavState<STATE>?> {
    val initialValue = navigationController.currentState?.let {
        NavState(it, NavigationAnimation.NO_ANIMATION)
    }

    return produceState<NavState<STATE>?>(
        key1 = navigationController,
        key2 = initialState,
        initialValue = initialValue,
    ) {
        navigationController.events
            .onSubscription {
                if (navigationController.backStackSize == 0) {
                    navigationController.navigateTo(initialState)
                }
            }.collect { event ->
                val animation = when (event) {
                    is NavigationEvents.InitialState -> NavigationAnimation.NO_ANIMATION
                    is NavigationEvents.OnNavigateTo -> NavigationAnimation.NAVIGATING
                    is NavigationEvents.OnPopUp -> NavigationAnimation.POPPING_UP
                    is NavigationEvents.OnStackEmpty -> NavigationAnimation.EXIT
                }
                value = NavState(event.state, animation)
            }
    }
}
