package week.on.a.plate.app.mainActivity.logic

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.imageFromGallery.ImageFromGalleryUseCase
import week.on.a.plate.app.mainActivity.logic.takePicture.TakePictureUseCase
import week.on.a.plate.app.mainActivity.logic.voice.VoiceInputUseCase
import week.on.a.plate.app.mainActivity.view.MainActivity
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogUseCase
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.NonPosed
import week.on.a.plate.screens.additional.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.additional.deleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screens.additional.filters.logic.FilterViewModel
import week.on.a.plate.screens.additional.inventory.logic.InventoryViewModel
import week.on.a.plate.screens.additional.ppAndTermsOfUse.logic.DocumentsWebViewModel
import week.on.a.plate.screens.additional.recipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.logic.SpecifyRecipeToCookPlanViewModel
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionViewModel
import week.on.a.plate.screens.additional.tutorial.logic.TutorialViewModel
import week.on.a.plate.screens.base.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.base.menu.presenter.logic.MenuViewModel
import week.on.a.plate.screens.base.searchRecipes.logic.SearchViewModel
import week.on.a.plate.screens.base.settings.logic.SettingsViewModel
import week.on.a.plate.screens.base.shoppingList.logic.ShoppingListViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogUseCase: DialogUseCase,
    val getSharedLinkUseCase: GetSharedLinkUseCase
) : ViewModel() {

    val actionPlusButton = mutableStateOf({})
    val voiceInputUseCase = VoiceInputUseCase(this)
    val imageFromGalleryUseCase = ImageFromGalleryUseCase(this)
    val takePictureUseCase = TakePictureUseCase(this)
    val snackbarHostState = SnackbarHostState()
    val navParams: MutableState<Any?> = mutableStateOf(null)
    val isActiveBaseScreen = mutableStateOf(true)
    val isActivePlusButton = mutableStateOf(true)
    val isActiveFilterScreen = mutableStateOf(false)

    lateinit var menuViewModel: MenuViewModel
    lateinit var specifySelectionViewModel: SpecifySelectionViewModel
    lateinit var filterViewModel: FilterViewModel
    lateinit var searchViewModel: SearchViewModel
    lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
    lateinit var shoppingListViewModel: ShoppingListViewModel
    lateinit var recipeCreateViewModel: RecipeCreateViewModel
    lateinit var inventoryViewModel: InventoryViewModel
    lateinit var deleteApplyViewModel: DeleteApplyViewModel
    lateinit var specifyRecipeToCookPlanViewModel: SpecifyRecipeToCookPlanViewModel
    lateinit var documentsWebViewModel: DocumentsWebViewModel
    lateinit var cookPlannerViewModel: CookPlannerViewModel

    fun initViewModels(
        specifySelection: SpecifySelectionViewModel,
        filter: FilterViewModel,
        search: SearchViewModel,
        recipeDetails: RecipeDetailsViewModel,
        shoppingList: ShoppingListViewModel,
        recipeCreate: RecipeCreateViewModel,
        menuView: MenuViewModel,
        inventory: InventoryViewModel,
        delete: DeleteApplyViewModel,
        cookPlanner: CookPlannerViewModel,
        specifyRecipeToCookPlan: SpecifyRecipeToCookPlanViewModel,
        settings: SettingsViewModel,
        tutorial: TutorialViewModel,
        documentsWeb: DocumentsWebViewModel,
        mainActivity: MainActivity
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
        specifyRecipeToCookPlanViewModel = specifyRecipeToCookPlan
        documentsWebViewModel = documentsWeb
        cookPlannerViewModel = cookPlanner

        val nonPosedText = mainActivity.getString(NonPosed.fullName)
        val forWeek = mainActivity.getString(ForWeek.fullName)
        specifySelectionViewModel.state.nonPosedText = nonPosedText

        menuViewModel.menuUIState.value.forWeekFullName.value = forWeek
        menuViewModel.menuUIState.value.nonPosedFullName.value = nonPosedText
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> onEvent(event)
            else -> dialogUseCase.onEvent(event)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.CloseDialog -> dialogUseCase.closeDialog()
            is MainEvent.OpenDialog -> dialogUseCase.openDialog(event.dialog)
            is MainEvent.ShowSnackBar -> showSnackBar(event.message)
            is MainEvent.Navigate -> navigate(event)
            MainEvent.HideDialog -> dialogUseCase.hide()
            MainEvent.ShowDialog -> dialogUseCase.show()
            is MainEvent.VoiceToText -> voiceToText(event.context, event.use)
        }
    }

    fun navigate(event: MainEvent.Navigate) {
        event.navParams.launch(this)
        navParams.value = event.destination
    }

    fun openDialog(dialog: DialogViewModel<*>) {
        onEvent(MainEvent.OpenDialog(dialog))
    }

    fun closeDialog() {
        onEvent(MainEvent.CloseDialog)
    }

    fun getCoroutineScope() = viewModelScope

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