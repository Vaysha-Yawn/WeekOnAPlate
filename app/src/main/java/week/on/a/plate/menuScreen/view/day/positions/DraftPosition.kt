package week.on.a.plate.menuScreen.view.day.positions

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
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.menuScreen.event.MenuEvent
import week.on.a.plate.menuScreen.event.NavFromMenuData
import week.on.a.plate.search.view.resultScreen.TagList

@Composable
fun DraftPosition(
    draft: Position.PositionDraftView,
    onEvent: (event: MenuEvent) -> Unit,
    rowScope: RowScope
) {
    with(rowScope) {
        Spacer(modifier = Modifier.width(20.dp))
        Row(Modifier
            .weight(3f)
            .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            TagList(draft.tags, draft.ingredients)
        }
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .clickable { onEvent(MenuEvent.SearchByDraft(draft)) },
        )
        Spacer(modifier = Modifier.width(10.dp))
    }

}