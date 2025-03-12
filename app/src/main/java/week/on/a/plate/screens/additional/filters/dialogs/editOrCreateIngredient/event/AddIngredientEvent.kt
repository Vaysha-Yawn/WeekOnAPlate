package week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.event


import android.content.Context
import week.on.a.plate.core.Event

sealed interface AddIngredientEvent : Event {
    class Done(val context: Context) : AddIngredientEvent
    object Close : AddIngredientEvent
    object ChooseCategory : AddIngredientEvent
    object PickImage : AddIngredientEvent
}