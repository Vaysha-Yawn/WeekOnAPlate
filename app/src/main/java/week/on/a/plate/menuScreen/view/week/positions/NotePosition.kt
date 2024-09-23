package week.on.a.plate.menuScreen.view.week.positions

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.core.Event
import week.on.a.plate.menuScreen.event.MenuEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekNotePosition(
    note: Position.PositionNoteView,
    onEvent: (event: Event) -> Unit
) {
    Column(
        Modifier
            .width(150.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .combinedClickable(
                onClick = {  },
                onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
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
            MoreButtonWithBackg {
                onEvent(MenuEvent.EditPosition(note))
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        TextSmall(
            note.note,
            modifier = Modifier,
        )

    }

}