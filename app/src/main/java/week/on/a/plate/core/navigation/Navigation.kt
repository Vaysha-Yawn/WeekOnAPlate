package week.on.a.plate.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.navigation.CategoriesSearchDestination
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.view.CategoriesSearchMain
import week.on.a.plate.core.fullScereenDialog.specifySelection.navigation.SpecifySelection
import week.on.a.plate.core.fullScereenDialog.specifySelection.view.SpecifySelectionMain
import week.on.a.plate.core.navigation.bottomBar.SearchScreen
import week.on.a.plate.core.navigation.bottomBar.MenuScreen
import week.on.a.plate.core.navigation.bottomBar.SettingsScreen
import week.on.a.plate.core.navigation.bottomBar.ShoppingListScreen
import week.on.a.plate.core.fullScereenDialog.filters.navigation.FilterDestination
import week.on.a.plate.core.fullScereenDialog.filters.view.FilterStart
import week.on.a.plate.menuScreen.view.main.MenuScreen
import week.on.a.plate.recipeFullScreen.navigation.RecipeDetailsDestination
import week.on.a.plate.recipeFullScreen.view.start.RecipeDetailsStart
import week.on.a.plate.search.view.main.SearchStart

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
        }
        composable<SettingsScreen> {
            viewModel.isActiveBaseScreen.value = true
        }

        composable<SpecifySelection> {
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
    }
}