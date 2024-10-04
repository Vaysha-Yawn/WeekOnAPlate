package week.on.a.plate.screenSpecifySelection.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.dateToLocalDate
import week.on.a.plate.core.utils.dateToStringShort
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.dialogDatePicker.view.DatePickerMy
import week.on.a.plate.screenMenu.view.day.positions.DraftPosition
import week.on.a.plate.screenMenu.view.day.positions.IngredientPosition
import week.on.a.plate.screenMenu.view.day.positions.NotePosition
import week.on.a.plate.screenSpecifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screenSpecifySelection.state.SpecifySelectionUIState
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarWithMenu(
    state: SpecifySelectionUIState,
    onEvent: (SpecifySelectionEvent) -> Unit,
) {
    val stateDatePicker = rememberDatePickerState()
    Scaffold(floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {
        DoneButton(
            text = stringResource(id = R.string.apply),
            Modifier.padding(horizontal = 24.dp)
        ) {
            onEvent(SpecifySelectionEvent.ApplyDate(stateDatePicker.selectedDateMillis?.dateToLocalDate()))
        }
    }) { pad ->
        Column {
            Box() {
                DatePickerMy(stateDatePicker,
                    Modifier
                        .offset(y = (-15).dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    onEvent(SpecifySelectionEvent.UpdatePreview(stateDatePicker.selectedDateMillis?.dateToLocalDate()))
                                },
                            )
                        })
                CloseButton {
                    onEvent(SpecifySelectionEvent.ApplyDate(stateDatePicker.selectedDateMillis?.dateToLocalDate()))
                }
            }
            Column(Modifier.offset(y = (-50).dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.find_replace),
                        contentDescription = ""
                    )
                    TextBody(
                        text = "Обновить",
                        Modifier.clickable {
                            onEvent(SpecifySelectionEvent.UpdatePreview(stateDatePicker.selectedDateMillis?.dateToLocalDate()))
                        },
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextBody(
                    text = "Превью для ${state.date.value?.dateToStringShort() ?: ""}",
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(Modifier.height(220.dp)) {
                    items(state.dayViewPreview.value.size) { index ->
                        FakeBlockSelection(
                            selection = state.dayViewPreview.value[index]
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
        pad
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCalendarWithMenu() {
    WeekOnAPlateTheme {
        val list = listOf(
            SelectionView(
                0, "Завтрак", LocalDate.now(), 0, false, mutableListOf(
                    Position.PositionNoteView(0, "Первое", 0),
                    Position.PositionNoteView(0, "Второе", 0),
                    Position.PositionNoteView(0, "Десерт", 0)
                )
            )
        )
        CalendarWithMenu(SpecifySelectionUIState().apply {
            isDateChooseActive.value = true
            dayViewPreview.value = list
            date.value = LocalDate.now()
        }) {}
    }
}

@Composable
fun FakeBlockSelection(
    selection: SelectionView,
) {
    TextTitle(
        selection.name,
        Modifier
            .padding(horizontal = 10.dp)
            .padding(start = 20.dp)
    )
    Spacer(Modifier.height(10.dp))
    for (rec in selection.positions) {
        FakeCardPosition(rec)
    }
    Spacer(Modifier.height(10.dp))
}


@Composable
fun FakeCardPosition(
    position: Position,
) {
    Card(
        Modifier
            .padding(bottom = 10.dp),
        shape = RectangleShape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when (position) {
                is Position.PositionDraftView -> {
                    DraftPosition(position, {}, this)
                }

                is Position.PositionIngredientView -> {
                    IngredientPosition(position, {}, this)
                }

                is Position.PositionNoteView -> {
                    NotePosition(position, {}, this)
                }

                is Position.PositionRecipeView -> {
                    FakeRecipePosition(position, this)
                }
            }
        }
    }
}

@Composable
fun FakeRecipePosition(
    recipe: Position.PositionRecipeView,
    rowScope: RowScope
) {
    with(rowScope) {
        Row(
            Modifier
                .weight(3f)
                .padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                Modifier
                    .weight(3f),
                horizontalAlignment = Alignment.Start,
            ) {
                SubText(
                    "${recipe.portionsCount} " + stringResource(R.string.Portions)
                )
                TextBody(
                    recipe.recipe.name
                )
            }
        }
    }
}