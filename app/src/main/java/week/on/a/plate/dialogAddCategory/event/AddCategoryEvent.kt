package week.on.a.plate.dialogAddCategory.event


import week.on.a.plate.core.Event

sealed class AddCategoryEvent: Event() {
    data object Done: AddCategoryEvent()
    data object Close: AddCategoryEvent()
}