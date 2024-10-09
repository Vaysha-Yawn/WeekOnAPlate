package week.on.a.plate.screenSearchRecipes.view.resultScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.screenSearchRecipes.event.SearchScreenEvent
import week.on.a.plate.screenSearchRecipes.state.SearchUIState

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
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(painterResource(R.drawable.sort), "")
            Icon(painterResource(R.drawable.sort), "")
        }
    }
}