package week.on.a.plate.search.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.search.data.SearchScreenEvent
import week.on.a.plate.search.data.SearchUIState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val stateUI = SearchUIState()

    init {
        viewModelScope.launch {
            //setFromDB
        }
    }

    fun onEvent(event: Event){
        when (event){
            is MainEvent -> onEvent(event)
            is SearchScreenEvent -> onEvent(event)
        }
    }

    fun onEvent(event: MainEvent){
        mainViewModel.onEvent(event)
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> TODO()
            SearchScreenEvent.VoiceSearch -> TODO()
            SearchScreenEvent.ClearResultSearch -> TODO()
            is SearchScreenEvent.FlipFavorite -> TODO()
        }
    }

}