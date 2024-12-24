
package utils.navigation.impl

import utils.navigation.NavStackEntry


class InMemoryNavStackEntryStore<STATE : Any> {


    @Suppress("UNCHECKED_CAST")
    fun get(forState: STATE): NavStackEntry<STATE> {
        return inMemoryValueStore.getOrPut(forState) {
            NavStackEntryImpl(forState)
        } as NavStackEntry<STATE>
    }

    fun dispose(forState: STATE) {
        inMemoryValueStore[forState]?.dispose()
        inMemoryValueStore.remove(forState)
    }

    companion object {
        private val inMemoryValueStore = mutableMapOf<Any, NavStackEntryImpl<Any>>()
    }
}
