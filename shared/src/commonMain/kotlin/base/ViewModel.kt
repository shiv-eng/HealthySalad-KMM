
package base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow

abstract class ViewModel<STATE>(protected val viewModelScope: CoroutineScope) {

    abstract val state: StateFlow<STATE>

    open fun onCleared() {
        viewModelScope.cancel()
    }
}

inline fun <VM : ViewModel<STATE>, STATE> viewModelFactory(provideViewModel: (CoroutineScope) -> VM): VM {
    val viewModelScope = ViewModelCoroutineScope()
    return provideViewModel(viewModelScope)
}
