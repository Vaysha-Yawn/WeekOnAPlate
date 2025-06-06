package week.on.a.plate.screens.additional.deleteApply.view

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainActivity
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screens.additional.deleteApply.state.DeleteApplyUIState

@Composable
fun DeleteApplyStart(
    viewModel: DeleteApplyViewModel,
    viewModel1: MainViewModel
) {
    val mainVM: MainViewModel = viewModel<MainViewModel>((LocalActivity.current as MainActivity))
    val onEvent = { eventData: DeleteApplyEvent ->
        viewModel.onEvent(eventData)
    }
    val state = viewModel.state
    DeleteApplyScreen(state, onEvent)
    MainEventResolve(viewModel.mainEvent, viewModel.dialogOpenParams, mainVM)
}

@Composable
private fun DeleteApplyScreen(
    state: DeleteApplyUIState,
    onEvent: (DeleteApplyEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        TextDisplayItalic(
            text = state.title.value,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(36.dp))
        TextTitle(text = state.message.value)
        Spacer(modifier = Modifier.weight(1f))
        DoneButton(
            stringResource(R.string.cancel)
        ) {
            onEvent(DeleteApplyEvent.Cancel)
        }
        Spacer(modifier = Modifier.height(24.dp))
        CommonButton(text = stringResource(R.string.delete)) {
            onEvent(DeleteApplyEvent.Apply)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDeleteApplyScreen() {
    WeekOnAPlateTheme {
        DeleteApplyScreen(DeleteApplyUIState().apply {
            message.value =
                "Внимание при удалении рецепта вы так же удалите и все позиции в меню с этим рецептом, эту операцию невозможно отменить."
        }) {}
    }
}