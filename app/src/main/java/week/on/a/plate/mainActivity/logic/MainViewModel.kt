package week.on.a.plate.mainActivity.logic

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.core.DialogUseCase
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.screens.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.deleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screens.filters.logic.FilterViewModel
import week.on.a.plate.screens.inventory.logic.InventoryViewModel
import week.on.a.plate.screens.menu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screens.recipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screens.searchRecipes.logic.SearchViewModel
import week.on.a.plate.screens.searchRecipes.logic.voice.VoiceInputUseCase
import week.on.a.plate.screens.shoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogUseCase: DialogUseCase,
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {

    lateinit var menuViewModel: week.on.a.plate.screens.menu.logic.MenuViewModel
    lateinit var specifySelectionViewModel: SpecifySelectionViewModel
    lateinit var filterViewModel: FilterViewModel
    lateinit var searchViewModel: SearchViewModel
    lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
    lateinit var shoppingListViewModel: ShoppingListViewModel
    lateinit var recipeCreateViewModel: RecipeCreateViewModel
    lateinit var inventoryViewModel: InventoryViewModel
    lateinit var deleteApplyViewModel: DeleteApplyViewModel

    fun initViewModels(
        specifySelection: SpecifySelectionViewModel,
        filter: FilterViewModel,
        search: SearchViewModel,
        recipeDetails: RecipeDetailsViewModel,
        shoppingList: ShoppingListViewModel,
        recipeCreate: RecipeCreateViewModel,
        menuView: week.on.a.plate.screens.menu.logic.MenuViewModel,
        inventory: InventoryViewModel,
        delete: DeleteApplyViewModel
    ) {
        specifySelectionViewModel = specifySelection
        filterViewModel = filter
        searchViewModel = search
        recipeDetailsViewModel = recipeDetails
        shoppingListViewModel = shoppingList
        recipeCreateViewModel = recipeCreate
        menuViewModel = menuView
        inventoryViewModel = inventory
        deleteApplyViewModel = delete

        specifySelectionViewModel.mainViewModel = this
        filterViewModel.mainViewModel = this
        searchViewModel.mainViewModel = this
        recipeDetailsViewModel.mainViewModel = this
        shoppingListViewModel.mainViewModel = this
        recipeCreateViewModel.mainViewModel = this
        menuViewModel.mainViewModel = this
        inventoryViewModel.mainViewModel = this
        deleteApplyViewModel.mainViewModel = this
    }

    lateinit var locale: Locale
    val actionPlusButton = mutableStateOf({})
    val voiceInputUseCase = VoiceInputUseCase()
    val snackbarHostState = SnackbarHostState()
    lateinit var nav: NavHostController
    val isActiveBaseScreen = mutableStateOf(true)
    val isActivePlusButton = mutableStateOf(true)
    val isActiveFilterScreen = mutableStateOf(false)

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
                    sCRUDRecipeInMenu.onEvent(event.actionMenuDBData)
                }
            }
            MainEvent.CloseDialog -> dialogUseCase.closeDialog()
            is MainEvent.OpenDialog -> dialogUseCase.openDialog(event.dialog)
            is MainEvent.ShowSnackBar -> showSnackBar(event.message)
            is MainEvent.Navigate -> nav.navigate(event.destination)
            MainEvent.NavigateBack -> nav.popBackStack()
            MainEvent.HideDialog -> dialogUseCase.hide()
            is MainEvent.ShowDialog -> dialogUseCase.show()
            is MainEvent.VoiceToText -> voiceToText(event.use)
        }
    }

    private fun voiceToText(use: (ArrayList<String>?) -> Unit) {
        viewModelScope.launch {
            voiceInputUseCase.start(use)
        }
    }

    private fun showSnackBar(message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }


}