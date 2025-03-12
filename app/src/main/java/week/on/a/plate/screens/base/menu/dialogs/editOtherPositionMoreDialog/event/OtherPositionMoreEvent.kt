package week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.event

import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.BaseContextProvider

sealed interface OtherPositionMoreEvent : Event {
    object Edit : OtherPositionMoreEvent
    object Double : OtherPositionMoreEvent
    object Delete : OtherPositionMoreEvent
    object Move : OtherPositionMoreEvent
    object Close : OtherPositionMoreEvent
    class AddToShopList(val contextProvider: BaseContextProvider) : OtherPositionMoreEvent
}