package week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionRoom

class SetPermanentMealsUIState {
    val show: MutableState<Boolean> = mutableStateOf(true)
    val selections = mutableStateOf(listOf<CategorySelectionRoom>())
}


