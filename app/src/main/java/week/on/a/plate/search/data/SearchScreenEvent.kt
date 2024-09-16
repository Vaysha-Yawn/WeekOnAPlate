package week.on.a.plate.search.data

import week.on.a.plate.menuScreen.data.eventData.DialogData

sealed class SearchScreenEvent {
    class ShowSnackBar(val message: String) : SearchScreenEvent()
    data object CloseDialog : SearchScreenEvent()
    data object NavigateBack : SearchScreenEvent()
    class OpenDialog(val dialog: DialogData) : SearchScreenEvent()
    class Navigate(val navData: NavFromSearchData) : SearchScreenEvent()
    class Search(val s: String) : SearchScreenEvent()
    class SearchFilters(s: String) : SearchScreenEvent()
    data object VoiceSearch : SearchScreenEvent()
    data object OpenFilter : SearchScreenEvent()
    data object VoiceSearchFilters : SearchScreenEvent()
    data object CloseFilters : SearchScreenEvent()
    data object ClearResultSearch : SearchScreenEvent()
}