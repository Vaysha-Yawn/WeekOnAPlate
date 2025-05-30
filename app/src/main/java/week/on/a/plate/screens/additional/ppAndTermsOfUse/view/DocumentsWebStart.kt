package week.on.a.plate.screens.additional.ppAndTermsOfUse.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.core.uitools.webview.WebPage
import week.on.a.plate.core.uitools.webview.WhenGoFromWebView
import week.on.a.plate.screens.additional.ppAndTermsOfUse.event.DocumentsWebEvent
import week.on.a.plate.screens.additional.ppAndTermsOfUse.logic.DocumentsWebViewModel
import week.on.a.plate.screens.additional.ppAndTermsOfUse.state.DocumentsWebUIState

@Composable
fun DocumentsWebStart(
    viewModel: DocumentsWebViewModel,
    viewModel1: MainViewModel
) {
    val onEvent = { event: Event ->
        viewModel.onEvent(event)
    }
    val state = viewModel.state
    DocumentWebContent(state, onEvent)
    MainEventResolve(viewModel.mainEvent, remember { mutableStateOf(null) }, viewModel1)
}

@Composable
private fun DocumentWebContent(
    state: DocumentsWebUIState, onEvent: (Event) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth()) {
            Icon(
                painterResource(R.drawable.back),
                "Back",
                Modifier
                    .padding(20.dp)
                    .size(48.dp)
                    .clickNoRipple { onEvent(DocumentsWebEvent.Back) }
            )
        }
        if (state.isForPP.value){
            WebPage(state.url, state.webViewPP, onEvent, WhenGoFromWebView.DefaultBrowser)
        }else{
            WebPage(state.url, state.webViewTerms, onEvent, WhenGoFromWebView.DefaultBrowser)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterScreen() {
    WeekOnAPlateTheme {
        DocumentWebContent(DocumentsWebUIState()) {}
    }
}