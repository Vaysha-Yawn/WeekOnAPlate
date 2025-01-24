package week.on.a.plate.core.uitools

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.clickNoRipple(click: () -> Unit): Modifier {
    return this.clickable(
        onClick = click,
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    )
}