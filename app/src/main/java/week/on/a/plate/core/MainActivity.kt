package week.on.a.plate.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import week.on.a.plate.menuScreen.logic.MenuViewModel
import week.on.a.plate.menuScreen.view.MenuScreen
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        enableEdgeToEdge()
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.week.collect { uiState ->
                    setContent {
                        WeekOnAPlateTheme {
                            Scaffold(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                            ) { innerPadding ->
                                innerPadding

                                MenuScreen(viewModel, uiState)
                            }
                        }
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeekOnAPlateTheme {

    }
}