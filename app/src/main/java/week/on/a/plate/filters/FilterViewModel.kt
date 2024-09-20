package week.on.a.plate.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val stateUI = FilterUIState()

    init {
        viewModelScope.launch {
            //setFromDB
        }
    }

    fun onEvent(event: Event){
        when (event){
            is MainEvent -> mainViewModel.onEvent(event)
            is FilterEvent -> onEvent(event)
        }
    }

    fun onEvent(event: FilterEvent) {
        when (event) {
            FilterEvent.VoiceSearch -> TODO()
            FilterEvent.VoiceSearchFilters -> TODO()
            FilterEvent.ClearResultSearch -> TODO()
            is FilterEvent.RemoveSelectedIngredientVoiceApply -> TODO()
            is FilterEvent.RemoveSelectedTag -> TODO()
            is FilterEvent.RemoveSelectedTagVoiceApply -> TODO()
            FilterEvent.VoiceApply -> TODO()
            is FilterEvent.RemoveSelectedIngredient -> TODO()
            is FilterEvent.SearchFilter -> TODO()
        }
    }

}