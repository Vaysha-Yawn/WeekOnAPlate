package week.on.a.plate.data.utils

import week.on.a.plate.core.utils.ListComparator

suspend fun <T> updateListOfEntity(
    oldList: List<T>,
    newList: List<T>,
    insertAction: suspend (List<T>) -> Unit,
    deleteAction: suspend (T) -> Unit
) {
    val itemsToAdd = ListComparator.addComparator(oldList, newList)
    val itemsToRemove = ListComparator.removedComparator(oldList, newList)

    if (itemsToAdd.isNotEmpty()) insertAction(itemsToAdd)
    if (itemsToRemove.isNotEmpty()) itemsToRemove.forEach { deleteAction(it) }
}