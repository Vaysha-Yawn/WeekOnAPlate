package week.on.a.plate.core.mainView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import week.on.a.plate.core.navigation.BottomBar
import week.on.a.plate.core.navigation.Navigation
import week.on.a.plate.ui.theme.ColorBackgroundWhite
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeekOnAPlateTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize().background(ColorBackgroundWhite),
                    bottomBar = {
                        BottomBar(navController)
                    }, snackbarHost = { SnackbarHost(hostState = viewModel.snackbarHostState) {
                        if (viewModel.snackbarHostState.currentSnackbarData != null) {
                            Snackbar(
                                snackbarData = viewModel.snackbarHostState.currentSnackbarData!!,
                                modifier = Modifier.padding(bottom = 80.dp),
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    }
                ) { innerPadding ->
                    Navigation(navController, innerPadding, viewModel.snackbarHostState)
                }
            }
        }
    }
}