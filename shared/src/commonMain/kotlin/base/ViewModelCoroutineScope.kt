package base

import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext


class ViewModelCoroutineScope : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    override fun close() {
        coroutineContext.cancel()
    }
}
