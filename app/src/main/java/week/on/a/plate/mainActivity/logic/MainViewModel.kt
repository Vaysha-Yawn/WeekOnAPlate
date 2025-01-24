package week.on.a.plate.mainActivity.logic

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.NonPosed
import week.on.a.plate.dialogs.core.DialogUseCase
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.imageFromGallery.ImageFromGalleryUseCase
import week.on.a.plate.mainActivity.logic.takePicture.TakePictureUseCase
import week.on.a.plate.mainActivity.logic.voice.VoiceInputUseCase
import week.on.a.plate.mainActivity.view.MainActivity
import week.on.a.plate.screens.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.deleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screens.documentsWeb.logic.DocumentsWebViewModel
import week.on.a.plate.screens.filters.logic.FilterViewModel
import week.on.a.plate.screens.inventory.logic.InventoryViewModel
import week.on.a.plate.screens.menu.logic.MenuViewModel
import week.on.a.plate.screens.recipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screens.searchRecipes.logic.SearchViewModel
import week.on.a.plate.screens.settings.logic.SettingsViewModel
import week.on.a.plate.screens.shoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screens.specifyRecipeToCookPlan.logic.SpecifyRecipeToCookPlanViewModel
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionViewModel
import week.on.a.plate.screens.tutorial.logic.TutorialViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogUseCase: DialogUseCase,
    val getSharedLinkUseCase: GetSharedLinkUseCase
) : ViewModel() {

    lateinit var locale: Locale
    val actionPlusButton = mutableStateOf({})
    val voiceInputUseCase = VoiceInputUseCase(this)
    val imageFromGalleryUseCase = ImageFromGalleryUseCase(this)
    val takePictureUseCase = TakePictureUseCase(this)
    val snackbarHostState = SnackbarHostState()
    lateinit var nav: NavHostController
    val isActiveBaseScreen = mutableStateOf(true)
    val isActivePlusButton = mutableStateOf(true)
    val isActiveFilterScreen = mutableStateOf(false)

    lateinit var settingsViewModel: SettingsViewModel
    lateinit var menuViewModel: MenuViewModel
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
    lateinit var tutorialViewModel: TutorialViewModel
    lateinit var documentsWebViewModel: DocumentsWebViewModel

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
        cookPlannerViewModel = cookPlanner
        specifyRecipeToCookPlanViewModel = specifyRecipeToCookPlan
        settingsViewModel = settings
        tutorialViewModel = tutorial
        documentsWebViewModel = documentsWeb

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
        searchViewModel.mainViewModel = this
        tutorialViewModel.mainViewModel = this
        settingsViewModel.mainViewModel = this
        documentsWebViewModel.mainViewModel = this


        //remove
        val nonPosedText = mainActivity.getString(NonPosed.fullName)
        val forWeek = mainActivity.getString(ForWeek.fullName)
        specifySelectionViewModel.state.nonPosedText = nonPosedText
        menuViewModel.menuUIState.forWeekFullName.value = forWeek
        menuViewModel.menuUIState.nonPosedFullName.value = nonPosedText

        specifySelectionViewModel.updateSelections()

        cookPlannerViewModel.initWithMainVM(this)
        recipeCreateViewModel.initWithMainVM(this)
        menuViewModel.initWithMainVM(this)
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
            is MainEvent.Navigate -> nav.navigate(event.destination)
            MainEvent.NavigateBack -> nav.popBackStack()
            MainEvent.HideDialog -> dialogUseCase.hide()
            MainEvent.ShowDialog -> dialogUseCase.show()
            is MainEvent.VoiceToText -> voiceToText(event.context, event.use)
            is MainEvent.UseSharedLink -> useSharedLink()
        }
    }

    fun openDialog(dialog: DialogViewModel<*>) {
        onEvent(MainEvent.OpenDialog(dialog))
    }

    fun closeDialog() {
        onEvent(MainEvent.CloseDialog)
    }

    fun getCoroutineScope() = viewModelScope

    private fun useSharedLink() {
        if (!getSharedLinkUseCase.isCheckedSharedAction && ::nav.isInitialized) {
            getSharedLinkUseCase.useSharedLink(viewModelScope, nav, recipeCreateViewModel)
        }else if (! ::nav.isInitialized){
            viewModelScope.launch {
                delay(500)
                onEvent(MainEvent.UseSharedLink)
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