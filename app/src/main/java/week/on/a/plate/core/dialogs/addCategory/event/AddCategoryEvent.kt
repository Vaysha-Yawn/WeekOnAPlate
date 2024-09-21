package week.on.a.plate.core.dialogs.addCategory.event


import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class AddCategoryEvent:Event() {
    data object Done: AddCategoryEvent()
    data object Close: AddCategoryEvent()
}