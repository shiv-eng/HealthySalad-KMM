package utils.navigation.impl


class Stack<E : Any>(initialItems: Collection<E> = emptyList()) {
    private val queue = ArrayDeque<E>(initialItems)


    val items: List<E> get() = queue.toList()

    val top: E? get() = queue.lastOrNull()


    val size: Int get() = queue.size


    fun push(element: E) {
        queue.addLast(element)
    }

    fun pop(): E {
        return queue.removeLast()
    }
}
