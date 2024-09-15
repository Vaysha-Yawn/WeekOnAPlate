package week.on.a.plate.menuScreen.view.week.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import week.on.a.plate.core.uitools.TagSmall
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.menuScreen.logic.eventData.DialogData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.NavFromMenuData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekDraftPosition(
    draft: Position.PositionDraftView,
    onEvent: (event: MenuEvent) -> Unit,
) {
    Column(
        Modifier
            .width(150.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .combinedClickable(
                onClick = { },
                onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.SearchByDraft(draft))) },
            )
            MoreButtonWithBackg {
                onEvent(MenuEvent.OpenDialog(DialogData.EditPosition(draft, onEvent)))
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            Modifier
                .padding(vertical = 5.dp),
        ) {
            draft.tags.forEach {
                    TagSmall(tag = it)
                    Spacer(modifier = Modifier.size(5.dp))
            }
            draft.ingredients.forEach{
                TagSmall(ingredientView = it)
                Spacer(modifier = Modifier.size(5.dp))
            }
        }
    }

}