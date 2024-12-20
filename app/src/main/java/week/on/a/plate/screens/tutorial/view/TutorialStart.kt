package week.on.a.plate.screens.tutorial.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.screens.tutorial.event.TutorialEvent
import week.on.a.plate.screens.tutorial.logic.TutorialViewModel
import week.on.a.plate.screens.tutorial.state.TutorialStateUI

@Composable
fun TutorialStart(
    vm: TutorialViewModel
) {
    TutorialContent(vm.stateUI) { event: TutorialEvent ->
        vm.onEvent(event)
    }
}

@Composable
fun TutorialContent(
    state: TutorialStateUI,
    onEvent: (TutorialEvent) -> Unit,
) {
    TutorialWrapper(state, onEvent) { scope ->
        with(scope) {
            ImageLoad(
                state.imgUri.value,
                Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 150.dp)
            )
            Empty()
            TextTitle(state.title.value)
            Empty()
            TextBody(state.description.value)
        }
    }
}

@Composable
fun TutorialWrapper(
    state: TutorialStateUI,
    onEvent: (TutorialEvent) -> Unit, content: @Composable (scope: ColumnScope) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.back),
                "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clickable {
                        onEvent(TutorialEvent.LastPage)
                    }
                    .size(36.dp)
            )

            Row {
                for (i in 0 until state.sizePages.intValue) {
                    RadioButton(
                        state.activePageInd.intValue == i,
                        {
                            onEvent(TutorialEvent.SelectPage(i))
                        },
                        colors = RadioButtonDefaults
                            .colors(selectedColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

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
            if (state.activePageInd.intValue == state.sizePages.intValue - 1) stringResource(
                R.string.next
            ) else stringResource(R.string.done)
        ) {
            if (state.activePageInd.intValue == state.sizePages.intValue - 1) {
                onEvent(TutorialEvent.Done)
            } else {
                onEvent(TutorialEvent.NextPage)
            }
        }
    }
}

@Composable
fun Empty(){
    Spacer(Modifier.size(24.dp  ))
}

@Preview(showBackground = true)
@Composable
fun PreviewTutorial() {
    WeekOnAPlateTheme {
        val state = TutorialStateUI()
        state.sizePages.intValue = 5
        state.activePageInd.intValue = 2
        state.title.value = "Создание рецепта"
        state.imgUri.value = "https???"
        state.description.value =
            "Чтобы создать рецепт, вы можете нажать на зелёную кнопку + на экране поиска рецептов или при поиске выбрать опцию создание рецепта."
        TutorialContent(state) {}
    }
}