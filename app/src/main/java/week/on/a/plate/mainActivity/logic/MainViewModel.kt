package week.on.a.plate.mainActivity.logic

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.example.emptyRecipe
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.dialogs.core.DialogUseCase
import week.on.a.plate.dialogs.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.exitApply.logic.ExitApplyViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.screens.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.deleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screens.filters.logic.FilterViewModel
import week.on.a.plate.screens.inventory.logic.InventoryViewModel
import week.on.a.plate.screens.menu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screens.recipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screens.recipeTimeline.logic.RecipeTimelineViewModel
import week.on.a.plate.screens.searchRecipes.logic.SearchViewModel
import week.on.a.plate.screens.searchRecipes.logic.imageFromGallery.ImageFromGalleryUseCase
import week.on.a.plate.screens.searchRecipes.logic.voice.VoiceInputUseCase
import week.on.a.plate.screens.settings.logic.SettingsViewModel
import week.on.a.plate.screens.shoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screens.specifyRecipeToCookPlan.logic.SpecifyRecipeToCookPlanViewModel
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionViewModel
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogUseCase: DialogUseCase,
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    lateinit var settingsViewModel: SettingsViewModel
    lateinit var menuViewModel: week.on.a.plate.screens.menu.logic.MenuViewModel
    lateinit var specifySelectionViewModel: SpecifySelectionViewModel
    lateinit var filterViewModel: FilterViewModel
    lateinit var searchViewModel: SearchViewModel
    lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
    lateinit var shoppingListViewModel: ShoppingListViewModel
    lateinit var recipeCreateViewModel: RecipeCreateViewModel
    lateinit var inventoryViewModel: InventoryViewModel
    lateinit var deleteApplyViewModel: DeleteApplyViewModel
    lateinit var cookPlannerViewModel: CookPlannerViewModel
    lateinit var specifyRecipeToCookPlanViewModel: SpecifyRecipeToCookPlanViewModel
    lateinit var recipeTimelineViewModel: RecipeTimelineViewModel

    fun initViewModels(
        specifySelection: SpecifySelectionViewModel,
        filter: FilterViewModel,
        search: SearchViewModel,
        recipeDetails: RecipeDetailsViewModel,
        shoppingList: ShoppingListViewModel,
        recipeCreate: RecipeCreateViewModel,
        menuView: week.on.a.plate.screens.menu.logic.MenuViewModel,
        inventory: InventoryViewModel,
        delete: DeleteApplyViewModel,
        cookPlanner: CookPlannerViewModel,
        specifyRecipeToCookPlan: SpecifyRecipeToCookPlanViewModel,
        recipeTimeline: RecipeTimelineViewModel,
        settings: SettingsViewModel
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
        cookPlannerViewModel = cookPlanner
        specifyRecipeToCookPlanViewModel = specifyRecipeToCookPlan
        recipeTimelineViewModel = recipeTimeline
        settingsViewModel = settings

        specifySelectionViewModel.mainViewModel = this
        filterViewModel.mainViewModel = this
        searchViewModel.mainViewModel = this
        recipeDetailsViewModel.mainViewModel = this
        shoppingListViewModel.mainViewModel = this
        recipeCreateViewModel.mainViewModel = this
        menuViewModel.mainViewModel = this
        inventoryViewModel.mainViewModel = this
        deleteApplyViewModel.mainViewModel = this
        cookPlannerViewModel.mainViewModel = this
        specifyRecipeToCookPlanViewModel.mainViewModel = this
        recipeTimelineViewModel.mainViewModel = this
        searchViewModel.mainViewModel = this
    }

    lateinit var locale: Locale
    val actionPlusButton = mutableStateOf({})
    val voiceInputUseCase = VoiceInputUseCase()
    val imageFromGalleryUseCase = ImageFromGalleryUseCase(this)
    val snackbarHostState = SnackbarHostState()
    lateinit var nav: NavHostController
    val isActiveBaseScreen = mutableStateOf(true)
    val isActivePlusButton = mutableStateOf(true)
    val isActiveFilterScreen = mutableStateOf(false)

    var isCheckedSharedAction = false
    var sharedLink = ""

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
            is MainEvent.VoiceToText -> voiceToText(event.context, event.use)
            is MainEvent.UseSharedLink -> useSharedLink(event.link)
            MainEvent.OpenDialogExitApplyFromCreateRecipe -> openDialogExitApplyFromCreateRecipe()
        }
    }

    private fun openDialogExitApplyFromCreateRecipe() {
        val vm = ExitApplyViewModel()
        vm.mainViewModel = this
        viewModelScope.launch {
            vm.launchAndGet {event->
                if (event == ExitApplyEvent.Exit){
                    nav.popBackStack()
                }
            }
        }
        onEvent(MainEvent.OpenDialog(vm))
    }

    private fun useSharedLink(text: String) {
        if (!isCheckedSharedAction && ::nav.isInitialized) {
            nav.navigate(RecipeCreateDestination)
            isCheckedSharedAction = true
            val recipeBase = emptyRecipe.apply {
                link = text
            }
            viewModelScope.launch {
                recipeCreateViewModel.launchAndGet(recipeBase, true) { recipe ->
                    viewModelScope.launch {
                        val newRecipe = RecipeView(
                            id = 0,
                            name = recipe.name.value,
                            description = recipe.description.value,
                            img = recipe.photoLink.value,
                            tags = recipe.tags.value,
                            standardPortionsCount = recipe.portionsCount.intValue,
                            ingredients = recipe.ingredients.value,
                            steps = recipe.steps.value.map {
                                RecipeStepView(
                                    0,
                                    it.description.value,
                                    it.image.value,
                                    it.timer.longValue,
                                    it.start,
                                    it.duration,
                                    it.pinnedIngredientsInd.value
                                )
                            },
                            link = recipe.source.value, false, LocalDateTime.now()
                        )
                        recipeRepository.create(newRecipe)
                    }
                }
            }
        }
    }

    private fun voiceToText(context: Context, use: (ArrayList<String>?) -> Unit) {
        viewModelScope.launch {
            voiceInputUseCase.start(context, use)
        }
    }

    private fun showSnackBar(message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }


}