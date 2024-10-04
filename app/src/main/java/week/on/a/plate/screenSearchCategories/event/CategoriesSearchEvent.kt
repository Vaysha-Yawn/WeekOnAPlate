package week.on.a.plate.screenSearchCategories.event


import week.on.a.plate.core.Event

sealed class CategoriesSearchEvent: Event() {
    class EditOrDelete(val text:String): CategoriesSearchEvent()
    class Select(val text:String): CategoriesSearchEvent()
    class Create(val text:String): CategoriesSearchEvent()
    data object VoiceSearch: CategoriesSearchEvent()
    data object Search: CategoriesSearchEvent()
    data object Close: CategoriesSearchEvent()
}