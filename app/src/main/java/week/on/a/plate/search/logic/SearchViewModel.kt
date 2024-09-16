package week.on.a.plate.search.logic

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.menuScreen.data.stateData.WeekState
import week.on.a.plate.menuScreen.logic.useCase.DialogManager
import week.on.a.plate.search.data.SearchScreenEvent
import week.on.a.plate.search.data.SearchUIState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dialogManager: DialogManager
) : ViewModel() {

    lateinit var snackbarHostState: SnackbarHostState
    lateinit var navController: NavHostController
    lateinit var bottomBarState: MutableState<Boolean>
    val stateUI = SearchUIState()

    init {
        viewModelScope.launch {
            //setFromDB
            stateUI.allCategories.value
        }
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            SearchScreenEvent.CloseDialog -> dialogManager.closeDialog()
            is SearchScreenEvent.Navigate -> {
                //navController.navigate(event.navData)
            }
            SearchScreenEvent.NavigateBack -> {
                navController.popBackStack()
            }
            is SearchScreenEvent.OpenDialog -> dialogManager.openDialog(event.dialog, viewModelScope)
            is SearchScreenEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(event.message)
                }
            }

            SearchScreenEvent.OpenFilter -> TODO()
            is SearchScreenEvent.Search -> TODO()
            SearchScreenEvent.VoiceSearch -> TODO()
            is SearchScreenEvent.SearchFilters -> TODO()
            SearchScreenEvent.VoiceSearchFilters -> TODO()
            SearchScreenEvent.CloseFilters -> TODO()
        }
    }

}