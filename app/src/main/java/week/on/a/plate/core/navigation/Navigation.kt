package week.on.a.plate.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.navigation.bottomBar.FavoritesScreen
import week.on.a.plate.core.navigation.bottomBar.HomeScreen
import week.on.a.plate.core.navigation.bottomBar.MenuScreen
import week.on.a.plate.core.navigation.bottomBar.SettingsScreen
import week.on.a.plate.core.navigation.bottomBar.ShoppingListScreen
import week.on.a.plate.fullScreenDialogs.navigation.FullScreenDialogRoute
import week.on.a.plate.fullScreenDialogs.navigation.CustomNavType
import week.on.a.plate.fullScreenDialogs.view.FullScreenDialogMain
import week.on.a.plate.menuScreen.view.main.MenuScreen
import week.on.a.plate.search.navigation.Search
import week.on.a.plate.search.view.main.SearchStart
import kotlin.reflect.typeOf

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    isActiveBaseScreen: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = MenuScreen,
        Modifier.padding(innerPadding)
    ) {
        //bottom bar
        composable<MenuScreen> {
            isActiveBaseScreen.value = true
            MenuScreen(snackbarHostState = snackbarHostState, navController = navController)
        }
        composable<HomeScreen> {
            isActiveBaseScreen.value = true
        }
        composable<ShoppingListScreen> {
            isActiveBaseScreen.value = true
        }
        composable<SettingsScreen> {
            isActiveBaseScreen.value = true
        }
        composable<FavoritesScreen> {
            isActiveBaseScreen.value = true
        }

        composable<SettingsScreen> {
            isActiveBaseScreen.value = true
        }

        // FullScreenDialogRoute

        composable<FullScreenDialogRoute.AddPositionToMenuDialog>(
            typeMap = mapOf(typeOf<Position.PositionRecipeView>() to CustomNavType.PositionRecipeView)
        ) {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.AddPositionToMenuDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }

        composable<FullScreenDialogRoute.DoublePositionToMenuDialog>(typeMap = mapOf(typeOf<Position>() to CustomNavType.Position)) {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.DoublePositionToMenuDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }

        composable<FullScreenDialogRoute.MovePositionToMenuDialog>(typeMap = mapOf(typeOf<Position>() to CustomNavType.Position)) {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.MovePositionToMenuDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }

        composable<FullScreenDialogRoute.SpecifyDateDialog> {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.SpecifyDateDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }

        // search
        composable<Search> {
            isActiveBaseScreen.value = true
            val arguments = it.toRoute<Search>()
            SearchStart(snackbarHostState, navController, isActiveBaseScreen, arguments)
        }

    }
}