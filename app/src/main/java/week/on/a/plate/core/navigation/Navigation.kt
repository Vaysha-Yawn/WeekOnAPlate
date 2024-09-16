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
import week.on.a.plate.core.navigation.destiations.FavoritesScreen
import week.on.a.plate.core.navigation.destiations.FullScreenDialogRoute
import week.on.a.plate.core.navigation.destiations.HomeScreen
import week.on.a.plate.core.navigation.destiations.MenuScreen
import week.on.a.plate.core.navigation.destiations.SettingsScreen
import week.on.a.plate.core.navigation.destiations.ShoppingListScreen
import week.on.a.plate.fullScreenDialogs.view.FullScreenDialogMain
import week.on.a.plate.menuScreen.view.main.MenuScreen

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

        // next false

        composable<FullScreenDialogRoute.AddPositionToMenuDialog> {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.AddPositionToMenuDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }

        composable<FullScreenDialogRoute.DoublePositionToMenuDialog> {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.DoublePositionToMenuDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }

        composable<FullScreenDialogRoute.MovePositionToMenuDialog> {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.MovePositionToMenuDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }

        composable<FullScreenDialogRoute.SpecifyDateDialog> {
            isActiveBaseScreen.value = false
            val arguments = it.toRoute<FullScreenDialogRoute.MovePositionToMenuDialog>()
            FullScreenDialogMain(snackbarHostState, navController, arguments)
        }


    }
}