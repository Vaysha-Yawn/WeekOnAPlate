package week.on.a.plate.dialogs.addPositionChoose.event

import week.on.a.plate.core.Event

sealed interface AddPositionEvent : Event {
    object AddRecipe : AddPositionEvent
    object AddIngredient : AddPositionEvent
    object AddDraft : AddPositionEvent
    object AddNote : AddPositionEvent
    object Close : AddPositionEvent
}