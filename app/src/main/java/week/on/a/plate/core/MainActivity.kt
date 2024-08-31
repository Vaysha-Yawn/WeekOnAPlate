package week.on.a.plate.core

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
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
        setContent {
            WeekOnAPlateTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)) { innerPadding ->
                    innerPadding

                       viewModel.testData.observe(this){
                            if (it!=null){
                                Log.e("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", "EEEE ${it}")
                            }
                        }
                        MenuScreen(viewModel)
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