package week.on.a.plate.core.utils

object ListComparator{
    fun <T>addComparator(oldList:List<T>, newList:List<T>): List<T> {
        val addT = newList.toMutableList()
        addT.removeAll(oldList)
        return addT
    }

    fun <T>removedComparator(oldList:List<T>, newList:List<T>): List<T> {
        val removedT = oldList.toMutableList()
        removedT.removeAll(newList)
        return removedT
    }
}

