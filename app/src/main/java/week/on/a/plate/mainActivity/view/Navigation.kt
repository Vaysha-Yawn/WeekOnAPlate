package week.on.a.plate.mainActivity.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import week.on.a.plate.screens.specifySelection.navigation.SpecifySelectionDirection
import week.on.a.plate.screens.specifySelection.view.SpecifySelectionMain
import week.on.a.plate.core.navigation.SearchScreen
import week.on.a.plate.core.navigation.MenuScreen
import week.on.a.plate.core.navigation.SettingsScreen
import week.on.a.plate.core.navigation.ShoppingListScreen
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.view.FilterStart
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.createRecipe.view.RecipeCreateStart
import week.on.a.plate.screens.deleteApply.navigation.DeleteApplyDirection
import week.on.a.plate.screens.deleteApply.view.DeleteApplyStart
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.inventory.navigation.InventoryDirection
import week.on.a.plate.screens.inventory.view.InventoryStart
import week.on.a.plate.screens.menu.view.main.MenuScreen
import week.on.a.plate.screens.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.recipeDetails.view.start.RecipeDetailsStart
import week.on.a.plate.screens.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.searchRecipes.view.main.SearchStart
import week.on.a.plate.screens.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.shoppingList.view.ShoppingListStart

@Composable
fun Navigation(
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = viewModel.nav,
        startDestination = MenuScreen,
        Modifier.padding(innerPadding)
    ) {
        //bottom bar
        composable<MenuScreen> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.menuViewModel.onEvent(week.on.a.plate.screens.menu.event.MenuEvent.GetSelIdAndCreate) }
            MenuScreen(
                viewModel.menuViewModel
            )
        }
        composable<SearchScreen> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.searchViewModel.onEvent(SearchScreenEvent.CreateRecipe) }
            SearchStart( viewModel)
        }
        composable<ShoppingListScreen> {
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.shoppingListViewModel.onEvent(ShoppingListEvent.Add) }
            ShoppingListStart(viewModel.shoppingListViewModel)
        }
        composable<SettingsScreen> {
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActiveFilterScreen.value = false
        }

        //others
        composable<SpecifySelectionDirection> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            SpecifySelectionMain(viewModel, viewModel.specifySelectionViewModel)
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

        composable<InventoryDirection> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            InventoryStart(viewModel.inventoryViewModel)
        }

        composable<DeleteApplyDirection> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            DeleteApplyStart(viewModel.deleteApplyViewModel)
        }
    }
}