package week.on.a.plate.mainActivity.view

import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import week.on.a.plate.core.theme.ColorBackgroundWhite
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenCreateRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screenFilters.logic.FilterViewModel
import week.on.a.plate.screenInventory.logic.InventoryViewModel
import week.on.a.plate.screenMenu.logic.MenuViewModel
import week.on.a.plate.screenRecipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screenSearchCategories.logic.CategoriesSearchViewModel
import week.on.a.plate.screenSearchRecipes.logic.SearchViewModel
import week.on.a.plate.screenShoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screenSpecifySelection.logic.SpecifySelectionViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val specifySelectionViewModel: SpecifySelectionViewModel by viewModels()
    private val categoriesSearchViewModel: CategoriesSearchViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()
    private val shoppingListViewModel: ShoppingListViewModel by viewModels()
    private val recipeCreateViewModel: RecipeCreateViewModel by viewModels()
    private val menuViewModel: MenuViewModel by viewModels()
    private val inventoryViewModel: InventoryViewModel by viewModels()

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

                viewModel.nav = rememberNavController()

                viewModel.initViewModels(
                    specifySelectionViewModel,
                    categoriesSearchViewModel,
                    filterViewModel,
                    searchViewModel,
                    recipeDetailsViewModel,
                    shoppingListViewModel,
                    recipeCreateViewModel,
                    menuViewModel,
                    inventoryViewModel
                )

                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(ColorBackgroundWhite),
                    bottomBar = {
                        BottomBar(viewModel.nav, viewModel.isActiveBaseScreen.value)
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
                    }
                ) { innerPadding ->
                    Navigation(
                        viewModel,
                        innerPadding,
                    )
                    DialogsContainer(viewModel.dialogUseCase.activeDialog.value) { event ->
                        viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}