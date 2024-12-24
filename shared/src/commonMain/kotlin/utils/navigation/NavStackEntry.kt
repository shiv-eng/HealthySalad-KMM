
package utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember


@Immutable
interface NavStackEntry<STATE : Any> {

    val state: STATE

    fun <T : Any> getOrPut(key: Any, compute: () -> T, onDispose: (T) -> Unit): T
}

@Composable
inline fun <T : Any> NavStackEntry<*>.rememberInNavStack(
    key: Any,
    noinline compute: @DisallowComposableCalls () -> T,
    noinline onDispose: @DisallowComposableCalls (T) -> Unit = {},
): T {
    return remember(key) { getOrPut(key, compute, onDispose) }
}

@Composable
inline fun <T : Any> rememberInNavStack(
    key: Any,
    noinline compute: @DisallowComposableCalls () -> T,
    noinline onDispose: @DisallowComposableCalls (T) -> Unit = {},
): T {
    return LocalNavStackEntry.current.rememberInNavStack(key, compute, onDispose)
}
