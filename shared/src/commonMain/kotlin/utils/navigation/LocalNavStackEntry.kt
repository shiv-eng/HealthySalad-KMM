
package utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalNavStackEntry = compositionLocalOf<NavStackEntry<out Any>> { error("Not implemented") }

@Composable
inline fun <STATE : Any> WithNavStackEntry(
    entry: NavStackEntry<STATE>,
    noinline content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalNavStackEntry provides entry, content = content)
}
