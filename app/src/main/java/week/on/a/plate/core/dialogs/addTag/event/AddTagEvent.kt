package week.on.a.plate.core.dialogs.addTag.event


import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class AddTagEvent:Event() {
    data object Done: AddTagEvent()
    data object Close: AddTagEvent()
    data object ChooseCategory: AddTagEvent()
}