package week.on.a.plate.dialogs.selectNStep.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.dialogs.selectNStep.event.SelectNStepEvent
import week.on.a.plate.dialogs.selectNStep.logic.SelectNStepViewModel

@Composable
fun SelectNStep(viewModel: SelectNStepViewModel) {
    val state = viewModel.state
    val onEvent = { event: SelectNStepEvent ->
        viewModel.onEvent(event)
    }
    Column(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(bottom = 24.dp)) {
            TextTitle(
                text = "Выберите шаг", textAlign = TextAlign.End)
        }


        for (step in 0 until state.stepCount) {
            TextTitle(
                text = (step + 1).toString() + " шаг",
                Modifier.clickable {
                    onEvent(SelectNStepEvent.Select(step))
                }.padding(bottom = 24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditOtherPanel() {
    WeekOnAPlateTheme {
        val vm = SelectNStepViewModel()
        LaunchedEffect(true) {
            vm.launchAndGet(5) {}
        }
        SelectNStep(vm)
    }
}