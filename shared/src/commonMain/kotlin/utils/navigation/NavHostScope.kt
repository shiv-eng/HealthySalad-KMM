
package utils.navigation

import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

interface NavHostScope<STATE : Any> {

    fun <S : STATE> OnState(
        key: KClass<S>,
        block: @Composable (entry: NavStackEntry<S>) -> Unit,
    )
}


inline fun <reified STATE : Any> NavHostScope<in STATE>.OnState(
    noinline block: @Composable (state: NavStackEntry<STATE>) -> Unit,
) {
    OnState(STATE::class, block)
}
