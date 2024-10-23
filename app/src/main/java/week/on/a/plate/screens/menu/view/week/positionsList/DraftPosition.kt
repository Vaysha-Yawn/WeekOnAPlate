package week.on.a.plate.screens.menu.view.week.positionsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.searchRecipes.view.resultScreen.TagListHidden

@Composable
fun DraftPosition(
    draft: Position.PositionDraftView,
    onEvent: (event: week.on.a.plate.screens.menu.event.MenuEvent) -> Unit,
    rowScope: RowScope
) {
    with(rowScope) {
        Spacer(modifier = Modifier.width(20.dp))
        Row(Modifier
            .weight(3f)
            .padding(vertical = 5.dp).clickable {  onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditOtherPosition(draft)) },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            TagListHidden(draft.tags, draft.ingredients)
        }
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .clickable { onEvent(week.on.a.plate.screens.menu.event.MenuEvent.SearchByDraft(draft)) },
        )
        Spacer(modifier = Modifier.width(10.dp))
        MoreButton {
            onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditPositionMore(draft))
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
}