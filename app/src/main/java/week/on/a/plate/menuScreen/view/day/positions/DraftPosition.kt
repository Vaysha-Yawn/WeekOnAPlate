package week.on.a.plate.menuScreen.view.day.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.TagSmall
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraftPosition(
    draft: Position.PositionDraftView,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit,
    rowScope: RowScope
) {
    with(rowScope){
        Spacer(modifier = Modifier.width(20.dp))
        LazyRow(
            Modifier
                .weight(3f)
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(draft.tags.size){
                    TagSmall(tag = draft.tags[it])
                    Spacer(modifier = Modifier.width(5.dp))
            }
            items(draft.ingredients.size){
                TagSmall(ingredientView = draft.ingredients[it])
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .clickable { onEvent(MenuEvent.Search(draft)) },
        )
        Spacer(modifier = Modifier.width(10.dp))
    }

}