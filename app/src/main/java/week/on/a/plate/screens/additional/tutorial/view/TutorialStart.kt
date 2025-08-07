package week.on.a.plate.screens.additional.tutorial.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextAnnotated
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.screens.additional.tutorial.event.TutorialEvent
import week.on.a.plate.screens.additional.tutorial.logic.TutorialViewModel
import week.on.a.plate.screens.additional.tutorial.state.TutorialEnum
import week.on.a.plate.screens.additional.tutorial.state.TutorialStateUI

@Composable
fun TutorialStart(
    viewModel: MainViewModel,
    target: TutorialEnum,
    vm: TutorialViewModel = hiltViewModel<TutorialViewModel>()
) {
    LaunchedEffect(target) {
        vm.launch(target)
    }
    TutorialContent(vm.stateUI) { event: TutorialEvent ->
        vm.onEvent(event)
    }
    MainEventResolve(vm.mainEvent, vm.dialogOpenParams, viewModel)
}

@Composable
private fun TutorialContent(
    state: TutorialStateUI,
    onEvent: (TutorialEvent) -> Unit,
) {
    TutorialWrapper(state, onEvent) { scope ->
        val page = state.tutorialEnum.value.pages[state.activePageInd.intValue]
        with(scope) {
            Image(
                painterResource(page.img), contentDescription = "",
                modifier = Modifier.fillMaxSize(0.6f),
                contentScale = ContentScale.Fit
            )
            Empty()
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (state.activePageInd.intValue != 0) {
                    Icon(
                        painterResource(R.drawable.back),
                        "Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .clickable {
                                onEvent(TutorialEvent.LastPage)
                            }
                            .size(24.dp)
                    )
                } else {
                    Empty()
                }
                for (i in 0 until state.tutorialEnum.value.pages.size) {
                    RadioButton(
                        state.activePageInd.intValue == i,
                        {
                            onEvent(TutorialEvent.SelectPage(i))
                        },
                        colors = RadioButtonDefaults
                            .colors(selectedColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.size(36.dp)
                    )
                }
                if (state.activePageInd.intValue != state.tutorialEnum.value.pages.size - 1) {
                    Icon(
                        painterResource(R.drawable.forward),
                        "Next",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .clickable {
                                onEvent(TutorialEvent.NextPage)
                            }
                            .size(24.dp)
                    )
                } else {
                    Empty()
                }
            }
            Empty()
            TextAnnotated(page.text, page.inlineContent)
        }
    }
}

@Composable
private fun TutorialWrapper(
    state: TutorialStateUI,
    onEvent: (TutorialEvent) -> Unit, content: @Composable (scope: ColumnScope) -> Unit
) {
    val page = state.tutorialEnum.value.pages[state.activePageInd.intValue]
    val sizePages = state.tutorialEnum.value.pages.size
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Empty()
            TextTitle(page.title)
            Icon(
                painterResource(R.drawable.close),
                "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clickable {
                        onEvent(TutorialEvent.Skip)
                    }
                    .size(36.dp)
            )
        }
        Empty()
        content(this)
        Empty()
        DoneButton(
            if (state.activePageInd.intValue != sizePages - 1) stringResource(
                R.string.next
            ) else stringResource(R.string.done)
        ) {
            if (state.activePageInd.intValue == sizePages - 1) {
                onEvent(TutorialEvent.Done)
            } else {
                onEvent(TutorialEvent.NextPage)
            }
        }
    }
}

@Composable
fun Empty() {
    Spacer(Modifier.size(24.dp))
}

@Preview(showBackground = true)
@Composable
fun PreviewTutorial() {
    WeekOnAPlateTheme {
        val state = TutorialStateUI()
        state.activePageInd.intValue = 0
        TutorialContent(state) {}
    }
}