package week.on.a.plate.screens.searchRecipes.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.ColorSubTextGrey
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
            .padding(vertical = 12.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painterResource(R.drawable.grid),
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
                    .padding(6.dp),
                tint = if (!state.modeResultViewIsList.value){
                    MaterialTheme.colorScheme.onBackground
                }else ColorSubTextGrey

            )
            Spacer(Modifier.width(12.dp))
            Icon(
                painterResource(R.drawable.list),
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
                    .padding(6.dp),
                tint = if (state.modeResultViewIsList.value){
                    MaterialTheme.colorScheme.onBackground
                }else ColorSubTextGrey
            )
        }


        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Icon(painterResource(R.drawable.sorting), "", modifier = Modifier.clickable {
                onEvent(SearchScreenEvent.SortMore)
            }.padding(6.dp))
            Spacer(Modifier.width(12.dp))
            Icon(painterResource(R.drawable.bookmark), "", modifier = Modifier.clickable {
                onEvent(SearchScreenEvent.SavePreset)
            }.padding(6.dp))
            Spacer(Modifier.width(12.dp))
            Icon(painterResource(R.drawable.more), "", modifier = Modifier.clickable {
                onEvent(SearchScreenEvent.FiltersMore)
            }.padding(6.dp))
           /* if (state.resultSortType.value.first == ResultSortType.date){
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
            }*/
        }
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.outline)
}