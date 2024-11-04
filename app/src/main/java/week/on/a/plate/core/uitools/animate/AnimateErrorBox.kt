package week.on.a.plate.core.uitools.animate

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun AnimateErrorBox(
    error: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    val off = remember { mutableStateOf(0.dp) }
    val xOffset = animateDpAsState(off.value, spring(), label = "error")
    LaunchedEffect(error.value) {
        if (error.value) {
            off.value = (-10).dp
            delay(80)
            off.value = (0).dp
            delay(80)
            off.value = (10).dp
            delay(80)
            off.value = (0).dp
            error.value = false
        }
    }

    Box(Modifier.offset(x = xOffset.value)) {
        content()
    }
}