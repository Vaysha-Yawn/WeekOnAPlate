package week.on.a.plate.screens.base.menu.view.day.positionsCard

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.event.MenuEvent
import week.on.a.plate.screens.base.searchRecipes.view.resultScreen.TagListHidden
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekDraftPosition(
    draft: Position.PositionDraftView,
    onEvent: (event: Event) -> Unit,
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .combinedClickable(
                onClick = { onEvent(MenuEvent.EditOtherPosition(draft)) },
                onLongClick = { onEvent(WrapperDatePickerEvent.SwitchEditMode) },
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search recipes by draft",
                modifier = Modifier
                    .size(24.dp)
                    .clickNoRipple { onEvent(MenuEvent.SearchByDraft(draft)) },
            )
            MoreButton {
                onEvent(MenuEvent.EditPositionMore(draft, context))
            }
        }
        Spacer(modifier = Modifier.size(6.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
        ) {
            TagListHidden(draft.tags, draft.ingredients)
        }
    }

}