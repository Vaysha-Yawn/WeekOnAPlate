package week.on.a.plate.data.repository.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.flowToState( startValue:T, scope: CoroutineScope): State<T> {
    val state = mutableStateOf<T>(startValue)
    scope.launch {
        this@flowToState.collect {
            state.value = it
        }
    }
    return state
}