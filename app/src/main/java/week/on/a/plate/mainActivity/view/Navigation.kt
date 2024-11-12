package week.on.a.plate.mainActivity.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import week.on.a.plate.core.navigation.CookPlannerDestination
import week.on.a.plate.screens.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.core.navigation.SettingsDestination
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.view.FilterStart
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.cookPlanner.view.CookPlannerStart
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.createRecipe.view.RecipeCreateStart
import week.on.a.plate.screens.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.deleteApply.view.DeleteApplyStart
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.inventory.view.InventoryStart
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.view.main.MenuScreen
import week.on.a.plate.screens.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.recipeDetails.view.start.RecipeDetailsStart
import week.on.a.plate.screens.recipeTimeline.navigation.RecipeTimelineDestination
import week.on.a.plate.screens.recipeTimeline.view.RecipeTimelineStart
import week.on.a.plate.screens.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.searchRecipes.view.main.SearchStart
import week.on.a.plate.screens.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.shoppingList.view.ShoppingListStart
import week.on.a.plate.screens.specifyRecipeToCookPlan.navigation.SpecifyForCookPlanDestination
import week.on.a.plate.screens.specifyRecipeToCookPlan.view.SpecifyForCookPlanStart
import week.on.a.plate.screens.specifySelection.view.SpecifySelectionAltStart

@Composable
fun Navigation(
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = viewModel.nav,
        startDestination = MenuDestination,
        Modifier.padding(innerPadding)
    ) {
        //bottom bar
        composable<MenuDestination> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.menuViewModel.onEvent(MenuEvent.GetSelIdAndCreate) }
            MenuScreen(
                viewModel.menuViewModel
            )
        }
        composable<SearchDestination> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.searchViewModel.onEvent(SearchScreenEvent.CreateRecipe) }
            SearchStart( viewModel)
        }
        composable<ShoppingListDestination> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.shoppingListViewModel.onEvent(ShoppingListEvent.Add) }
            ShoppingListStart(viewModel.shoppingListViewModel)
        }
        composable<SettingsDestination> {
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActiveFilterScreen.value = false
        }

        composable<CookPlannerDestination> {
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActiveFilterScreen.value = false
            CookPlannerStart(viewModel.cookPlannerViewModel)
        }

        //others
        composable<SpecifySelectionDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            SpecifySelectionAltStart(viewModel.specifySelectionViewModel)
        }

        composable<RecipeTimelineDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            RecipeTimelineStart(viewModel.recipeTimelineViewModel)
        }

        composable<FilterDestination>() {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = true
            viewModel.actionPlusButton.value =
                { viewModel.filterViewModel.onEvent(FilterEvent.CreateActive) }
            FilterStart(viewModel.filterViewModel)
        }

        composable<RecipeDetailsDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            RecipeDetailsStart(viewModel.recipeDetailsViewModel)
        }

        composable<RecipeCreateDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            RecipeCreateStart(viewModel.recipeCreateViewModel)
        }

        composable<InventoryDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            InventoryStart(viewModel.inventoryViewModel)
        }

        composable<DeleteApplyDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            DeleteApplyStart(viewModel.deleteApplyViewModel)
        }

        composable<SpecifyForCookPlanDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            SpecifyForCookPlanStart(viewModel.specifyRecipeToCookPlanViewModel)
        }
    }
}