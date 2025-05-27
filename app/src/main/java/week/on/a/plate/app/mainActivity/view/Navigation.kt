package week.on.a.plate.app.mainActivity.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.navigation.CookPlannerDestination
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.core.navigation.SettingsDestination
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.additional.createRecipe.view.base.RecipeCreateStart
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.deleteApply.view.DeleteApplyStart
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.view.FilterStart
import week.on.a.plate.screens.additional.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.additional.inventory.view.InventoryStart
import week.on.a.plate.screens.additional.ppAndTermsOfUse.navigation.DocumentsWebDestination
import week.on.a.plate.screens.additional.ppAndTermsOfUse.view.DocumentsWebStart
import week.on.a.plate.screens.additional.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.additional.recipeDetails.view.start.RecipeDetailsStart
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.navigation.SpecifyForCookPlanDestination
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.view.SpecifyForCookPlanStart
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.additional.specifySelection.view.SpecifySelectionAltStart
import week.on.a.plate.screens.additional.tutorial.navigation.TutorialDestination
import week.on.a.plate.screens.additional.tutorial.view.TutorialStart
import week.on.a.plate.screens.base.cookPlanner.view.CookPlannerStart
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.view.main.MenuScreen
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.view.main.SearchStart
import week.on.a.plate.screens.base.settings.view.SettingsStart
import week.on.a.plate.screens.base.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.base.shoppingList.view.ShoppingListStart

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = MenuDestination,
        Modifier.padding(innerPadding)
    ) {
        //bottom bar
        composable<MenuDestination> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            val context = LocalContext.current
            viewModel.actionPlusButton.value =
                { viewModel.menuViewModel.onEvent(MenuEvent.GetSelIdAndCreate (context)) }
            MenuScreen(viewModel, viewModel.menuViewModel)
        }
        composable<SearchDestination> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.searchViewModel.onEvent(SearchScreenEvent.CreateRecipe) }
            SearchStart(viewModel.searchViewModel, viewModel)
        }
        composable<ShoppingListDestination> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.shoppingListViewModel.onEvent(ShoppingListEvent.Add) }
            ShoppingListStart(viewModel.shoppingListViewModel, viewModel)
        }
        composable<SettingsDestination> {
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActiveFilterScreen.value = false
            SettingsStart(viewModel)
        }

        composable<CookPlannerDestination> {
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActiveFilterScreen.value = false
            CookPlannerStart(viewModel)
        }

        //others
        composable<SpecifySelectionDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            SpecifySelectionAltStart(viewModel.specifySelectionViewModel, viewModel)
        }

        composable<TutorialDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            TutorialStart(viewModel)
        }

        composable<FilterDestination>() {
            val context = LocalContext.current
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = true
            viewModel.actionPlusButton.value =
                { viewModel.filterViewModel.onEvent(FilterEvent.CreateActive(context)) }
            FilterStart(viewModel.filterViewModel, viewModel)
        }

        composable<RecipeDetailsDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            RecipeDetailsStart(viewModel.recipeDetailsViewModel, viewModel)
        }

        composable<RecipeCreateDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            RecipeCreateStart(viewModel.recipeCreateViewModel, viewModel)
        }

        composable<InventoryDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            InventoryStart(viewModel.inventoryViewModel, viewModel)
        }

        composable<DeleteApplyDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            DeleteApplyStart(viewModel.deleteApplyViewModel, viewModel)
        }

        composable<SpecifyForCookPlanDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            SpecifyForCookPlanStart(viewModel.specifyRecipeToCookPlanViewModel, viewModel)
        }

        composable<DocumentsWebDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            DocumentsWebStart(viewModel.documentsWebViewModel, viewModel)
        }
    }
}