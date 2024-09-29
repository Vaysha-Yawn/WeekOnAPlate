package week.on.a.plate.mainActivity.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import week.on.a.plate.screenSearchCategories.navigation.CategoriesSearchDestination
import week.on.a.plate.screenSearchCategories.view.CategoriesSearchMain
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelectionDirection
import week.on.a.plate.screenSpecifySelection.view.SpecifySelectionMain
import week.on.a.plate.core.navigation.SearchScreen
import week.on.a.plate.core.navigation.MenuScreen
import week.on.a.plate.core.navigation.SettingsScreen
import week.on.a.plate.core.navigation.ShoppingListScreen
import week.on.a.plate.screenFilters.navigation.FilterDestination
import week.on.a.plate.screenFilters.view.FilterStart
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenCreateRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screenCreateRecipe.view.RecipeCreateStart
import week.on.a.plate.screenMenu.view.main.MenuScreen
import week.on.a.plate.screenRecipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screenRecipeDetails.view.start.RecipeDetailsStart
import week.on.a.plate.screenSearchRecipes.view.main.SearchStart
import week.on.a.plate.screenShoppingList.view.ShoppingListStart

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
            MenuScreen(
                viewModel, viewModel.nav
            )
        }
        composable<SearchScreen> {
            viewModel.isActiveBaseScreen.value = true
            SearchStart( viewModel)
        }
        composable<ShoppingListScreen> {
            viewModel.isActiveBaseScreen.value = true
            ShoppingListStart(viewModel.shoppingListViewModel)
        }
        composable<SettingsScreen> {
            viewModel.isActiveBaseScreen.value = true
        }

        //others
        composable<SpecifySelectionDirection> {
            viewModel.isActiveBaseScreen.value = false
            SpecifySelectionMain(viewModel, viewModel.specifySelectionViewModel)
        }

        composable<FilterDestination>() {
            viewModel.isActiveBaseScreen.value = false
            FilterStart(viewModel.filterViewModel)
        }

        composable<CategoriesSearchDestination> {
            viewModel.isActiveBaseScreen.value = false
            CategoriesSearchMain(viewModel.categoriesSearchViewModel)
        }

        composable<RecipeDetailsDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.recipeDetailsViewModel.launch(0)
            RecipeDetailsStart(viewModel.recipeDetailsViewModel)
        }
        composable<RecipeCreateDestination> {
            viewModel.isActiveBaseScreen.value = false
            RecipeCreateStart(viewModel.recipeCreateViewModel)
        }
    }
}