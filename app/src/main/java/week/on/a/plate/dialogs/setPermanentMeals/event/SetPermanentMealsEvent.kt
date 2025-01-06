package week.on.a.plate.dialogs.setPermanentMeals.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionRoom

sealed class SetPermanentMealsEvent: Event() {
    data object Add: SetPermanentMealsEvent()
    data class Delete (val sel: CategorySelectionRoom): SetPermanentMealsEvent()
    data class Edit(val sel: CategorySelectionRoom): SetPermanentMealsEvent()
    data object Close: SetPermanentMealsEvent()
}