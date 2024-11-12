package week.on.a.plate.mainActivity.view

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import week.on.a.plate.core.theme.ColorBackgroundWhite
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.buttons.ActionPlusButton
import week.on.a.plate.dialogs.core.DialogsContainer
import week.on.a.plate.dialogs.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.exitApply.logic.ExitApplyViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.deleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screens.filters.logic.FilterViewModel
import week.on.a.plate.screens.inventory.logic.InventoryViewModel
import week.on.a.plate.screens.recipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screens.recipeTimeline.logic.RecipeTimelineViewModel
import week.on.a.plate.screens.searchRecipes.logic.SearchViewModel
import week.on.a.plate.screens.shoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screens.specifyRecipeToCookPlan.logic.SpecifyRecipeToCookPlanViewModel
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val specifySelectionViewModel: SpecifySelectionViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()
    private val shoppingListViewModel: ShoppingListViewModel by viewModels()
    private val recipeCreateViewModel: RecipeCreateViewModel by viewModels()
    private val menuViewModel: week.on.a.plate.screens.menu.logic.MenuViewModel by viewModels()
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private val deleteApplyViewModel: DeleteApplyViewModel by viewModels()
    private val cookPlannerViewModel: CookPlannerViewModel by viewModels()
    private val specifyRecipeToCookPlanViewModel: SpecifyRecipeToCookPlanViewModel by viewModels()
    private val recipeTimelineViewModel: RecipeTimelineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val voiceInputLauncher = registerForActivityResult(
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
        viewModel.voiceInputUseCase.voiceInputLauncher = voiceInputLauncher

        setContent {
            WeekOnAPlateTheme {
                viewModel.locale = LocalContext.current.resources.configuration.locales[0]

                viewModel.nav = rememberNavController()

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
                    recipeTimelineViewModel
                )

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

    override fun onBackPressed() {
        val destination = viewModel.nav.currentBackStackEntry?.destination
        val isCreateRecipe = destination?.hierarchy?.any { it.route?.substringBefore("?") == RecipeCreateDestination::class.qualifiedName } == true
        if (isCreateRecipe){
            viewModel.onEvent(MainEvent.OpenDialogExitApplyFromCreateRecipe)
        }else{
            super.onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == Intent.ACTION_SEND) {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT) ?: return
            viewModel.sharedLink = text
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.sharedLink != "" && !viewModel.isCheckedSharedAction) {
            viewModel.onEvent(MainEvent.UseSharedLink(viewModel.sharedLink))
        }
    }
}