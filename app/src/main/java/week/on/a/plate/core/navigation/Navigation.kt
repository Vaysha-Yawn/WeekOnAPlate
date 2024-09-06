package week.on.a.plate.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import week.on.a.plate.menuScreen.main.MenuScreen

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = MenuScreen,
        Modifier.padding(innerPadding)
    ) {
        composable<MenuScreen> {
            MenuScreen()
            Box(modifier = Modifier)
        }
        composable<HomeScreen> {
            Box(modifier = Modifier)
        }
        composable<ShoppingListScreen> {
            Box(modifier = Modifier)
        }
        composable<SettingsScreen> {
            Box(modifier = Modifier)
        }
        composable<FavoritesScreen> {
            Box(modifier = Modifier)
        }
    }
}