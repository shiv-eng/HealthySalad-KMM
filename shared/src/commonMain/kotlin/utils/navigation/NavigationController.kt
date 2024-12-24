
package utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.flow.SharedFlow
import utils.navigation.impl.NavigationControllerImpl
import utils.navigation.impl.NavigationEvents
import utils.navigation.impl.Stack


interface NavigationController<STATE> {

    val backStackSize: Int

    val currentState: STATE?


    fun navigateTo(newState: STATE)

    fun navigateUp()

    val events: SharedFlow<NavigationEvents<STATE>>
}

@Composable
fun <STATE : Any, SAVABLE> rememberNavigationController(
    onSave: (STATE) -> SAVABLE,
    onRestore: (SAVABLE) -> STATE,
): NavigationController<STATE> {
    return rememberSaveable(
        Unit,
        saver = listSaver(
            save = { it.backStack.map { state -> onSave(state) } },
            restore = { savables ->
                NavigationControllerImpl(stack = Stack(savables.map { onRestore(it) }))
            },
        ),
    ) { NavigationControllerImpl() }
}
