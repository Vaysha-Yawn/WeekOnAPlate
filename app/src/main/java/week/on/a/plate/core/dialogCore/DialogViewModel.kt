package week.on.a.plate.core.dialogCore

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class DialogViewModel<T>(
    val viewModelScope: CoroutineScope,
    val openDialog: (DialogViewModel<*>) -> Unit,
    val closeDialog: () -> Unit,
    val useResult: suspend (T) -> Unit
) {
    val show = mutableStateOf(true)
    protected lateinit var resultFlow: MutableStateFlow<T?>

    init {
        start()
    }

    private fun getResultFlow(): Flow<T?> {
        val flow = MutableStateFlow<T?>(null)
        resultFlow = flow
        return flow
    }

    private fun start() {
        openDialog(this)
        viewModelScope.launch {
            val flow = getResultFlow()
            flow.collect { value ->
                if (value != null) {
                    viewModelScope.launch {
                        useResult(value)
                    }
                }
            }
        }
    }

    protected fun done(result: T) {
        close()
        resultFlow.value = result
    }

    protected fun close() {
        show.value = false
        closeDialog()
    }

}
