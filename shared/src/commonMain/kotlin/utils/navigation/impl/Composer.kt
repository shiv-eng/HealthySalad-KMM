
package utils.navigation.impl

import androidx.compose.runtime.Composable
import utils.navigation.NavStackEntry


fun interface Composer<STATE : BASE, BASE : Any> {

    @Composable
    fun compose(entry: NavStackEntry<STATE>)
}


fun <STATE : BASE, BASE : Any> Composer(
    block: @Composable (NavStackEntry<STATE>) -> Unit,
): Composer<STATE, BASE> {
    return ComposerImpl(block)
}


class ComposerImpl<STATE : BASE, BASE : Any>(
    val block: @Composable (NavStackEntry<STATE>) -> Unit,
) : Composer<STATE, BASE> {
    @Composable
    override fun compose(entry: NavStackEntry<STATE>) {
        block(entry)
    }
}
