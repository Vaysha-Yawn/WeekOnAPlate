package week.on.a.plate.data.repository.utils

suspend fun <T> updateListOfEntity(
    oldList: List<T>,
    newList: List<T>,
    findSameInList: suspend (T, List<T>) -> T?,
    insertAction: suspend (T) -> Unit,
    deleteAction: suspend (T) -> Unit,
    updateAction: suspend (T) -> Unit,
) {
    oldList.forEach { old ->
        val new = findSameInList(old, newList)
        if (new == null){
            deleteAction(old)
        }else if (new!=old){
            updateAction(new)
        }
    }

    newList.forEach { new->
        val old = findSameInList(new, oldList)
        if (old == null){
            insertAction(new)
        }
    }
}