package week.on.a.plate.mainActivity.logic

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.screenSearchCategories.logic.CategoriesSearchViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.screenFilters.logic.FilterViewModel
import week.on.a.plate.screenSpecifySelection.logic.SpecifySelectionViewModel
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenRecipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screenSearchRecipes.logic.SearchViewModel
import week.on.a.plate.screenSearchRecipes.logic.voice.VoiceInputUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogUseCase: DialogUseCase,
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {
    val voiceInputUseCase = VoiceInputUseCase()

    val snackbarHostState = SnackbarHostState()
    lateinit var nav: NavHostController
    val isActiveBaseScreen = mutableStateOf(true)

    //other viewModels
    val specifySelectionViewModel = SpecifySelectionViewModel(sCRUDRecipeInMenu.menuR)
    val categoriesSearchViewModel = CategoriesSearchViewModel()
    val filterViewModel = FilterViewModel()
    val searchViewModel = SearchViewModel(sCRUDRecipeInMenu)
    val recipeDetailsViewModel = RecipeDetailsViewModel()

    init {
        specifySelectionViewModel.mainViewModel = this
        categoriesSearchViewModel.mainViewModel = this
        filterViewModel.mainViewModel = this
        searchViewModel.mainViewModel = this
        recipeDetailsViewModel.mainViewModel = this
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> onEvent(event)
            else -> dialogUseCase.onEvent(event)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ActionDBMenu -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(event.actionMenuDBData, listOf())
                }
            }

            MainEvent.CloseDialog -> dialogUseCase.closeDialog()
            is MainEvent.GetSelIdAndCreate -> TODO()
            is MainEvent.OpenDialog -> dialogUseCase.openDialog(event.dialog)
            is MainEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(event.message)
                }
            }

            is MainEvent.Navigate -> {
                nav.navigate(event.destination)
            }

            MainEvent.NavigateBack -> {
                nav.popBackStack()
            }

            MainEvent.HideDialog -> dialogUseCase.hide()
            is MainEvent.ShowDialog -> dialogUseCase.show()
            is MainEvent.VoiceToText -> {
                viewModelScope.launch {
                    voiceInputUseCase.start(event.use)
                }
            }
        }
    }


}