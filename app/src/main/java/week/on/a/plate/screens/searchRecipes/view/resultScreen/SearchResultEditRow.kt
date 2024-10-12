package week.on.a.plate.screens.searchRecipes.view.resultScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.screens.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.searchRecipes.state.ResultSortType
import week.on.a.plate.screens.searchRecipes.state.ResultSortingDirection
import week.on.a.plate.screens.searchRecipes.state.SearchUIState

@Composable
fun SearchResultEditRow(state: SearchUIState, onEvent: (SearchScreenEvent) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painterResource(R.drawable.select_all),
                "",
                modifier = Modifier
                    .clickable {
                        state.modeResultViewIsList.value = false
                    }
                    .background(
                        if (state.modeResultViewIsList.value) {
                            MaterialTheme.colorScheme.surface
                        } else {
                            MaterialTheme.colorScheme.primary
                        }, CircleShape
                    )
                    .padding(6.dp)
            )
            Icon(
                painterResource(R.drawable.checklist),
                "",
                modifier = Modifier
                    .clickable {
                        state.modeResultViewIsList.value = true
                    }
                    .background(
                        if (!state.modeResultViewIsList.value) {
                            MaterialTheme.colorScheme.surface
                        } else {
                            MaterialTheme.colorScheme.primary
                        }, CircleShape
                    )
                    .padding(6.dp)
            )
        }
        Row(
            Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.resultSortType.value.first == ResultSortType.date){
                Icon(painterResource(R.drawable.calendar), "", modifier = Modifier.clickable {
                    onEvent(SearchScreenEvent.ChangeSort(ResultSortType.alphabet, state.resultSortType.value.second))
                })
            }else{
                Icon(painterResource(R.drawable.sort_by_alpha), "", modifier = Modifier.clickable {
                    onEvent(SearchScreenEvent.ChangeSort(ResultSortType.date, state.resultSortType.value.second))
                })
            }
            Spacer(Modifier.size(6.dp))
            if (state.resultSortType.value.second == ResultSortingDirection.up){
                Icon(painterResource(R.drawable.arrow_up), "", modifier = Modifier.clickable {
                    onEvent(SearchScreenEvent.ChangeSort(state.resultSortType.value.first, ResultSortingDirection.down))
                })
            }else{
                Icon(painterResource(R.drawable.arrow_down), "", modifier = Modifier.clickable {
                    onEvent(SearchScreenEvent.ChangeSort(state.resultSortType.value.first, ResultSortingDirection.up))
                })
            }
        }
    }
}