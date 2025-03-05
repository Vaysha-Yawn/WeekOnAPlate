package week.on.a.plate.dialogs.forMenuScreen.editOtherPositionMore.event

import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.BaseContextProvider

sealed class OtherPositionMoreEvent: Event() {
    data object Edit: OtherPositionMoreEvent()
    data object Double: OtherPositionMoreEvent()
    data object Delete: OtherPositionMoreEvent()
    data object Move: OtherPositionMoreEvent()
    data object Close: OtherPositionMoreEvent()
    data class AddToShopList(val contextProvider: BaseContextProvider): OtherPositionMoreEvent()
}