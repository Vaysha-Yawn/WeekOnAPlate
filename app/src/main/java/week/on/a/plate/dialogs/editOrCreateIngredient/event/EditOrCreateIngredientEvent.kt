package week.on.a.plate.dialogs.editOrCreateIngredient.event


import android.content.Context
import week.on.a.plate.core.Event

sealed interface EditOrCreateIngredientEvent : Event {
    class Done(val context: Context) : EditOrCreateIngredientEvent
    object Close : EditOrCreateIngredientEvent
    object ChooseCategory : EditOrCreateIngredientEvent
    object PickImage : EditOrCreateIngredientEvent
}