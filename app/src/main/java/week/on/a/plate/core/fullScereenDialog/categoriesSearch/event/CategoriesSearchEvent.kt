package week.on.a.plate.core.fullScereenDialog.categoriesSearch.event


import week.on.a.plate.core.Event

sealed class CategoriesSearchEvent: Event() {
    class Select(val text:String): CategoriesSearchEvent()
    class Create(val text:String): CategoriesSearchEvent()
    data object VoiceSearch: CategoriesSearchEvent()
    data object Search: CategoriesSearchEvent()
    data object Close: CategoriesSearchEvent()
}