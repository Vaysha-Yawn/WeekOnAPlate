package week.on.a.plate.dialogs.setPermanentMeals.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionRoom
import week.on.a.plate.dialogs.setPermanentMeals.event.SetPermanentMealsEvent
import week.on.a.plate.dialogs.setPermanentMeals.state.SetPermanentMealsUIState
import week.on.a.plate.screens.cookPlanner.view.normalizeTimeToText
import week.on.a.plate.screens.filters.view.clickNoRipple
import java.time.LocalTime

@Composable
fun SetPermanentMealsStart(
    state: SetPermanentMealsUIState,
    onEvent: (SetPermanentMealsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(text = "Настроить постоянные приёмы пищи")
        Spacer(modifier = Modifier.height(36.dp))
        for (sel in state.selections.value) {
            CategoriesSelectionRow(sel, onEvent)
            Spacer(modifier = Modifier.height(12.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(stringResource(R.string.add)) {
            onEvent(SetPermanentMealsEvent.Add)
        }
    }
}

@Composable
fun CategoriesSelectionRow(sel: CategorySelectionRoom, onEvent: (SetPermanentMealsEvent) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp)).padding(vertical = 6.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(painterResource(R.drawable.edit), "", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.clickNoRipple {
            onEvent(SetPermanentMealsEvent.Edit(sel))
        })
        Spacer(modifier = Modifier.width(24.dp))
        TextBody("${sel.stdTime.hour}:${sel.stdTime.minute.normalizeTimeToText()} - ${sel.name}")
        Spacer(modifier = Modifier.width(24.dp))
        Icon(painterResource(R.drawable.delete), "", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.clickNoRipple {
            onEvent(SetPermanentMealsEvent.Delete(sel))
        })
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewChangePortionsPanel() {
    WeekOnAPlateTheme {
        SetPermanentMealsStart(SetPermanentMealsUIState().apply {
            selections.value = listOf(CategorySelectionRoom("Завтрак", LocalTime.now()))
        }) {}
    }
}