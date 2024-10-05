package week.on.a.plate.mainActivity.logic

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.screenCreateRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screenDeleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screenFilters.logic.FilterViewModel
import week.on.a.plate.screenInventory.logic.InventoryViewModel
import week.on.a.plate.screenMenu.logic.MenuViewModel
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenRecipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screenSearchCategories.logic.CategoriesSearchViewModel
import week.on.a.plate.screenSearchRecipes.logic.SearchViewModel
import week.on.a.plate.screenSearchRecipes.logic.voice.VoiceInputUseCase
import week.on.a.plate.screenShoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screenSpecifySelection.logic.SpecifySelectionViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogUseCase: DialogUseCase,
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {

    lateinit var menuViewModel: MenuViewModel
    lateinit var specifySelectionViewModel: SpecifySelectionViewModel
    lateinit var categoriesSearchViewModel: CategoriesSearchViewModel
    lateinit var filterViewModel: FilterViewModel
    lateinit var searchViewModel: SearchViewModel
    lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
    lateinit var shoppingListViewModel: ShoppingListViewModel
    lateinit var recipeCreateViewModel: RecipeCreateViewModel
    lateinit var inventoryViewModel: InventoryViewModel
    lateinit var deleteApplyViewModel: DeleteApplyViewModel
    lateinit var locale: Locale

    fun initViewModels(
        specifySelection: SpecifySelectionViewModel,
        categoriesSearch: CategoriesSearchViewModel,
        filter: FilterViewModel,
        search: SearchViewModel,
        recipeDetails: RecipeDetailsViewModel,
        shoppingList: ShoppingListViewModel,
        recipeCreate: RecipeCreateViewModel,
        menuView: MenuViewModel,
        inventory: InventoryViewModel,
        delete: DeleteApplyViewModel
    ) {
        specifySelectionViewModel = specifySelection
        categoriesSearchViewModel = categoriesSearch
        filterViewModel = filter
        searchViewModel = search
        recipeDetailsViewModel = recipeDetails
        shoppingListViewModel = shoppingList
        recipeCreateViewModel = recipeCreate
        menuViewModel = menuView
        inventoryViewModel = inventory
        deleteApplyViewModel = delete

        specifySelectionViewModel.mainViewModel = this
        categoriesSearchViewModel.mainViewModel = this
        filterViewModel.mainViewModel = this
        searchViewModel.mainViewModel = this
        recipeDetailsViewModel.mainViewModel = this
        shoppingListViewModel.mainViewModel = this
        recipeCreateViewModel.mainViewModel = this
        menuViewModel.mainViewModel = this
        inventoryViewModel.mainViewModel = this
        deleteApplyViewModel.mainViewModel = this
    }

    val voiceInputUseCase = VoiceInputUseCase()
    val snackbarHostState = SnackbarHostState()
    lateinit var nav: NavHostController
    val isActiveBaseScreen = mutableStateOf(true)

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