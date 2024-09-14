package week.on.a.plate.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import week.on.a.plate.menuScreen.view.main.MenuScreen

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = MenuScreen,
        Modifier.padding(innerPadding)
    ) {
        composable<MenuScreen> {
            MenuScreen(snackbarHostState = snackbarHostState)
        }
        composable<HomeScreen> {

        }
        composable<ShoppingListScreen> {

        }
        composable<SettingsScreen> {

        }
        composable<FavoritesScreen> {

        }
    }
}