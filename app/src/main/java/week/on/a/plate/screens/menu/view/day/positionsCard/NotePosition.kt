package week.on.a.plate.screens.menu.view.day.positionsCard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekNotePosition(
    note: Position.PositionNoteView,
    onEvent: (event: Event) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .combinedClickable(
                onClick = { onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditOtherPosition(note)) },
                onLongClick = { onEvent(WrapperDatePickerEvent.SwitchEditMode) },
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(id = R.drawable.note),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp),
            )
            MoreButton {
                onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditPositionMore(note))
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        TextBody(
            note.note,
            modifier = Modifier,
        )

    }

}