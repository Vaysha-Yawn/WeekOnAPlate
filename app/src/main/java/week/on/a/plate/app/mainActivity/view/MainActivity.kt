package week.on.a.plate.app.mainActivity.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.theme.ColorBackgroundWhite
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.buttons.ActionPlusButton
import week.on.a.plate.dialogs.core.DialogsContainer
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
import week.on.a.plate.screens.base.menu.logic.MenuViewModel
import week.on.a.plate.screens.base.searchRecipes.logic.SearchViewModel
import week.on.a.plate.screens.base.settings.logic.SettingsViewModel
import week.on.a.plate.screens.base.shoppingList.logic.ShoppingListViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val specifySelectionViewModel: SpecifySelectionViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()
    private val shoppingListViewModel: ShoppingListViewModel by viewModels()
    private val recipeCreateViewModel: RecipeCreateViewModel by viewModels()
    private val menuViewModel: MenuViewModel by viewModels()
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private val deleteApplyViewModel: DeleteApplyViewModel by viewModels()
    private val cookPlannerViewModel: CookPlannerViewModel by viewModels()
    private val specifyRecipeToCookPlanViewModel: SpecifyRecipeToCookPlanViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val documentsWebViewModel: DocumentsWebViewModel by viewModels()

    private val voiceInputLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val recognizedText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            viewModel.viewModelScope.launch {
                viewModel.voiceInputUseCase.processVoiceResult(recognizedText)
            }
        }
    }

    private val getPictureLauncher = registerForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.imageFromGalleryUseCase.saveImage(this, uri)
    }

    private val getPhotoLauncher = registerForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { result: Boolean ->
        if (result) {
            viewModel.takePictureUseCase.saveImage(this)
        } else {
            viewModel.takePictureUseCase.close()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        viewModel.voiceInputUseCase.voiceInputLauncher = voiceInputLauncher
        viewModel.imageFromGalleryUseCase.imageLauncher = getPictureLauncher
        viewModel.takePictureUseCase.imageLauncher = getPhotoLauncher

        viewModel.initViewModels(
            specifySelectionViewModel,
            filterViewModel,
            searchViewModel,
            recipeDetailsViewModel,
            shoppingListViewModel,
            recipeCreateViewModel,
            menuViewModel,
            inventoryViewModel,
            deleteApplyViewModel,
            cookPlannerViewModel,
            specifyRecipeToCookPlanViewModel,
            settingsViewModel,
            tutorialViewModel,
            documentsWebViewModel,
            this@MainActivity
        )

        setContent {
            viewModel.locale = LocalContext.current.resources.configuration.locales[0]
            viewModel.nav = rememberNavController()

            WeekOnAPlateTheme {

                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .background(ColorBackgroundWhite),
                    bottomBar = {
                        BottomBar(
                            viewModel.nav,
                            viewModel.isActiveBaseScreen.value,
                            searchViewModel
                        )
                    }, snackbarHost = {
                        SnackbarHost(hostState = viewModel.snackbarHostState) {
                            if (viewModel.snackbarHostState.currentSnackbarData != null) {
                                Snackbar(
                                    snackbarData = viewModel.snackbarHostState.currentSnackbarData!!,
                                    modifier = Modifier.padding(bottom = 80.dp),
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }, floatingActionButton = {
                        if (viewModel.isActivePlusButton.value && viewModel.dialogUseCase.activeDialog.value == null) {
                            val pad = if (viewModel.isActiveFilterScreen.value) {
                                50.dp
                            } else {
                                0.dp
                            }
                            ActionPlusButton(Modifier.padding(bottom = pad)) {
                                viewModel.actionPlusButton.value()
                            }
                        }
                    }
                ) { innerPadding ->
                    Navigation(
                        viewModel,
                        innerPadding,
                    )
                    DialogsContainer (viewModel.dialogUseCase.activeDialog.value) { event ->
                        viewModel.onEvent(event)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == Intent.ACTION_SEND) {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT) ?: return
            viewModel.getSharedLinkUseCase.setLink(text)
        }
    }

    //todo: test
    override fun onRestart() {
        super.onRestart()

        //SharedLinkUse
        if (intent.action == Intent.ACTION_SEND ){
            val text = intent.getStringExtra(Intent.EXTRA_TEXT) ?: return
            viewModel.getSharedLinkUseCase.setLink(text)
            viewModel.getSharedLinkUseCase.checkAndStart {
                viewModel.onEvent(MainEvent.UseSharedLink)
            }
        }else{
            viewModel.getSharedLinkUseCase.checkAndStart {
                viewModel.onEvent(MainEvent.UseSharedLink)
            }
        }
    }
}