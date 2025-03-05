package week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.event


import android.content.Context
import week.on.a.plate.core.Event

sealed class AddIngredientEvent: Event() {
    data class Done(val context:Context): AddIngredientEvent()
    data object Close: AddIngredientEvent()
    data object ChooseCategory: AddIngredientEvent()
    data object PickImage : AddIngredientEvent()
}