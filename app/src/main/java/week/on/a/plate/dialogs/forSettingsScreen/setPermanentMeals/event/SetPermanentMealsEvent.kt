package week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionRoom

sealed interface SetPermanentMealsEvent : Event {
    object Add : SetPermanentMealsEvent
    class Delete(val sel: CategorySelectionRoom) : SetPermanentMealsEvent
    class Edit(val sel: CategorySelectionRoom) : SetPermanentMealsEvent
    object Close : SetPermanentMealsEvent
}