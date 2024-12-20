package week.on.a.plate.screens.documentsWeb.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.webview.WebPage
import week.on.a.plate.screens.documentsWeb.event.DocumentsWebEvent
import week.on.a.plate.screens.documentsWeb.logic.DocumentsWebViewModel
import week.on.a.plate.screens.filters.view.clickNoRipple

@Composable
fun DocumentsWebStart(
    viewModel: DocumentsWebViewModel
) {
    val onEvent = { event: Event ->
        viewModel.onEvent(event)
    }
    val state = viewModel.state

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth()) {
            Icon(painterResource(R.drawable.back), "", Modifier.clickNoRipple { onEvent(DocumentsWebEvent.Back) })
        }
        WebPage(state.url, state.webView, onEvent, false)
    }
}