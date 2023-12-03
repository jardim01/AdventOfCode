package y2022.day9.domain

data class Node<T>(var value: T, var previous: Node<T>?, var next: Node<T>?) {
    fun tail(): Node<T> {
        var tail = this
        while (true) tail = tail.next ?: return tail
    }

    fun head(): Node<T> {
        var head = this
        while (true) head = head.previous ?: return head
    }
}
