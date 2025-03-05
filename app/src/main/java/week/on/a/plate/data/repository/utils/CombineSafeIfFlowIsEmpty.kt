package week.on.a.plate.data.repository.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

inline fun <reified T> Collection<Flow<T>>.combineSafeIfFlowIsEmpty(): Flow<List<T>> {
    if (isEmpty()) return flowOf(emptyList())
    return combine(this) { it.toList() }
}