package week.on.a.plate.dialogAddPosition.event

import week.on.a.plate.core.Event

sealed class AddPositionEvent: Event() {
    data object AddRecipe: AddPositionEvent()
    data object AddIngredient: AddPositionEvent()
    data object AddDraft: AddPositionEvent()
    data object AddNote: AddPositionEvent()
    data object Close: AddPositionEvent()
}