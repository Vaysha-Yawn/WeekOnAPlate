package week.on.a.plate.app.mainActivity.logic

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    lateinit var nav: NavHostController
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

        filterViewModel.mainViewModel = this
        recipeCreateViewModel.mainViewModel = this
        inventoryViewModel.mainViewModel = this
        deleteApplyViewModel.mainViewModel = this
        specifyRecipeToCookPlanViewModel.mainViewModel = this
        tutorial.mainViewModel = this
        settings.mainViewModel = this
        documentsWebViewModel.mainViewModel = this


        val nonPosedText = mainActivity.getString(NonPosed.fullName)
        val forWeek = mainActivity.getString(ForWeek.fullName)
        specifySelectionViewModel.state.nonPosedText = nonPosedText

        menuViewModel.menuUIState.value.forWeekFullName.value = forWeek
        menuViewModel.menuUIState.value.nonPosedFullName.value = nonPosedText

        specifySelectionViewModel.updateSelections()

        recipeCreateViewModel.initWithMainVM(this)
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> onEvent(event)
            else -> dialogUseCase.onEvent(event)
        }
    }

    //todo можно вместо nav в view model создать flow который будет отслеживать события навигации в главном экране и передовать эти события туда
    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.CloseDialog -> dialogUseCase.closeDialog()
            is MainEvent.OpenDialog -> dialogUseCase.openDialog(event.dialog)
            is MainEvent.ShowSnackBar -> showSnackBar(event.message)
            is MainEvent.Navigate -> navigate(event)
            MainEvent.NavigateBack -> nav.popBackStack()
            MainEvent.HideDialog -> dialogUseCase.hide()
            MainEvent.ShowDialog -> dialogUseCase.show()
            is MainEvent.VoiceToText -> voiceToText(event.context, event.use)
            is MainEvent.UseSharedLink -> useSharedLink()
        }
    }

    fun navigate(event: MainEvent.Navigate) {
        nav.navigate(event.destination)
        event.navParams.launch(this)
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