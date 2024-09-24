package week.on.a.plate.core

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogs.DialogManager
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.logic.CategoriesSearchViewModel
import week.on.a.plate.core.fullScereenDialog.filters.logic.FilterViewModel
import week.on.a.plate.core.fullScereenDialog.specifySelection.logic.SpecifySelectionViewModel
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.recipeFullScreen.logic.RecipeDetailsViewModel
import week.on.a.plate.search.logic.SearchViewModel
import week.on.a.plate.search.logic.voice.VoiceInputUseCase
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogManager: DialogManager,
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
            else -> dialogManager.onEvent(event)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ActionDBMenu -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(event.actionMenuDBData, listOf())
                }
            }

            MainEvent.CloseDialog -> dialogManager.closeDialog()
            is MainEvent.GetSelIdAndCreate -> TODO()
            is MainEvent.OpenDialog -> dialogManager.openDialog(event.dialog)
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

            MainEvent.HideDialog -> dialogManager.hide()
            is MainEvent.ShowDialog -> dialogManager.show()
            is MainEvent.VoiceToText -> {
                viewModelScope.launch {
                    voiceInputUseCase.start(event.use)
                }
            }
        }
    }


}