package week.on.a.plate.filters

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogs.logic.DialogManager
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.search.data.SearchScreenEvent
import week.on.a.plate.search.data.SearchUIState
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