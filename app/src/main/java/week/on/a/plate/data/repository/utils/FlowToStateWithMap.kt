package week.on.a.plate.data.repository.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T, R> Flow<T>.flowToStateWithMap( startValue:R, scope: CoroutineScope, map: T.()->R): State<R> {
    val state = mutableStateOf<R>(startValue)
    scope.launch {
        this@flowToStateWithMap.collect {
            state.value = it.map()
        }
    }
    return state
}