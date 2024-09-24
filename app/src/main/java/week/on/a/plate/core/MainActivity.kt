package week.on.a.plate.core

import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import week.on.a.plate.core.navigation.bottomBar.BottomBar
import week.on.a.plate.core.navigation.Navigation
import week.on.a.plate.core.dialogs.DialogsContainer
import week.on.a.plate.ui.theme.ColorBackgroundWhite
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val voiceInputLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val recognizedText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                viewModel.viewModelScope.launch {
                    viewModel.voiceInputUseCase.processVoiceResult(recognizedText)
                }
            }
        }
        viewModel.voiceInputUseCase.voiceInputLauncher = voiceInputLauncher

        setContent {
            WeekOnAPlateTheme {
                viewModel.nav = rememberNavController()
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(ColorBackgroundWhite),
                    bottomBar = {
                        BottomBar(viewModel.nav, viewModel.isActiveBaseScreen.value)
                    }, snackbarHost = {
                        SnackbarHost(hostState = viewModel.snackbarHostState) {
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
                    Navigation(
                        viewModel,
                        innerPadding,
                    )
                   DialogsContainer(viewModel.dialogManager.activeDialog.value){event->
                        viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}