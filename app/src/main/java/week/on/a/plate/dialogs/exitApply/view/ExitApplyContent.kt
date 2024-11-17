package week.on.a.plate.dialogs.exitApply.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.dialogs.exitApply.event.ExitApplyEvent

@Composable
fun ExitApplyContent(onEvent: (ExitApplyEvent) -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(24.dp)
    ) {
        TextTitle(
            stringResource(R.string.ask_exit),
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(24.dp))
        TextSmall(stringResource(R.string.exit_tip))
        Spacer(Modifier.height(48.dp))
        DoneButton(stringResource(R.string.stay)) {
            onEvent(ExitApplyEvent.Close)
        }
        Spacer(Modifier.height(12.dp))
        CommonButton(stringResource(R.string.exit)) {
            onEvent(ExitApplyEvent.Exit)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExitApply() {
    WeekOnAPlateTheme {
        ExitApplyContent {}
    }
}