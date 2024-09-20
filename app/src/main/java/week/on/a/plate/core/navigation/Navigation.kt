package week.on.a.plate.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.core.navigation.bottomBar.FavoritesScreen
import week.on.a.plate.core.navigation.bottomBar.HomeScreen
import week.on.a.plate.core.navigation.bottomBar.MenuScreen
import week.on.a.plate.core.navigation.bottomBar.SettingsScreen
import week.on.a.plate.core.navigation.bottomBar.ShoppingListScreen
import week.on.a.plate.filters.FilterStart
import week.on.a.plate.filters.navigation.FilterCustomNavType
import week.on.a.plate.filters.navigation.FilterRoute
import week.on.a.plate.fullScreenDialogs.navigation.CustomNavType
import week.on.a.plate.fullScreenDialogs.navigation.FullScreenDialogRoute
import week.on.a.plate.fullScreenDialogs.view.FullScreenDialogMain
import week.on.a.plate.menuScreen.view.main.MenuScreen
import week.on.a.plate.search.navigation.SearchRoute
import week.on.a.plate.search.view.main.SearchStart
import kotlin.reflect.typeOf

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
                viewModel
            )
        }
        composable<HomeScreen> {
            viewModel.isActiveBaseScreen.value = true
        }
        composable<ShoppingListScreen> {
            viewModel.isActiveBaseScreen.value = true
        }
        composable<SettingsScreen> {
            viewModel.isActiveBaseScreen.value = true
        }
        composable<FavoritesScreen> {
            viewModel.isActiveBaseScreen.value = true
        }

        composable<SettingsScreen> {
            viewModel.isActiveBaseScreen.value = true
        }

        // FullScreenDialogRoute

        composable<FullScreenDialogRoute.AddPositionToMenuDialog>(
            typeMap = mapOf(typeOf<Position.PositionRecipeView>() to CustomNavType.PositionRecipeView)
        ) {
            viewModel.isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.AddPositionToMenuDialog>()
            FullScreenDialogMain(arguments, viewModel)
        }

        composable<FullScreenDialogRoute.DoublePositionToMenuDialog>(typeMap = mapOf(typeOf<Position>() to CustomNavType.Position)) {
            viewModel.isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.DoublePositionToMenuDialog>()
            FullScreenDialogMain(arguments, viewModel)
        }

        composable<FullScreenDialogRoute.MovePositionToMenuDialog>(typeMap = mapOf(typeOf<Position>() to CustomNavType.Position)) {
            viewModel.isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.MovePositionToMenuDialog>()
            FullScreenDialogMain(arguments, viewModel)
        }

        composable<FullScreenDialogRoute.SpecifyDateDialog> {
            viewModel.isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.SpecifyDateDialog>()
            FullScreenDialogMain(arguments, viewModel)
        }

        // search
        composable<SearchRoute.SearchDestination> {
            viewModel.isActiveBaseScreen.value = true
            val arguments = it.toRoute<SearchRoute.SearchDestination>()
            SearchStart(arguments, viewModel)
        }

        composable<SearchRoute.SearchWithSelId> {
            viewModel.isActiveBaseScreen.value = true
            val arguments = it.toRoute<SearchRoute.SearchWithSelId>()
            SearchStart(arguments, viewModel)
        }

        composable<FilterRoute.FilterDestination>(
            typeMap = mapOf(
                typeOf<List<RecipeTagView>>() to FilterCustomNavType.RecipeTagView,
                typeOf<List<IngredientView>>() to FilterCustomNavType.IngredientView,
            )
        ) {
            viewModel.isActiveBaseScreen.value = false
            val arguments = it.toRoute<FilterRoute.FilterDestination>()
            FilterStart(arguments = arguments, mainVM = viewModel)
        }

        composable<FilterRoute.FilterToCreateDraftWithSelIdDestination> {
            viewModel.isActiveBaseScreen.value = false
            val arguments = it.toRoute<FilterRoute.FilterToCreateDraftWithSelIdDestination>()
            FilterStart(arguments = arguments, mainVM = viewModel)
        }

    }
}