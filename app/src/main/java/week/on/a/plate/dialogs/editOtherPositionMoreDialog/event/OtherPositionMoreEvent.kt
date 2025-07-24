package week.on.a.plate.dialogs.editOtherPositionMoreDialog.event

import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.BaseContextProvider

sealed interface OtherPositionMoreEvent : Event {
    object Edit : OtherPositionMoreEvent
    object Delete : OtherPositionMoreEvent
    object Move : OtherPositionMoreEvent
    object Close : OtherPositionMoreEvent
    class AddToCart(val contextProvider: BaseContextProvider) : OtherPositionMoreEvent
}