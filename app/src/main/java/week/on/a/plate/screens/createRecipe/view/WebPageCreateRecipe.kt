package week.on.a.plate.screens.createRecipe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.WebPage
import week.on.a.plate.core.uitools.buttons.DoneButtonSmall
import week.on.a.plate.core.uitools.buttons.TextButton
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

@Composable
fun WebPageCreateRecipe(state: RecipeCreateUIState, onEvent: (Event) -> Unit) {
    if (state.source.value == ""){
        DoneButtonSmall(text = "Искать по названию рецепта в интернете", Modifier.padding(24.dp)) {
            state.source.value =
                "https://www.google.ru/search?q=Рецепт ${state.name.value.replace(" ", "+")}"
        }
    }else{
        WebPage(url = state.source, state.webview, onEvent)
    }
}

@Composable
fun RowWebActions(state: RecipeCreateUIState) {
    Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        TextButton("Назад") {
            if (state.webview.value?.canGoBack()==true){
                state.webview.value?.goBack()
            }
        }
        Spacer(Modifier.width(10.dp))
        TextButton("Обновить") {
            state.webview.value?.reload()
        }
        Spacer(Modifier.width(10.dp))
        TextButton("Искать заново") {
            state.source.value =
                "https://www.google.ru/search?q=Рецепт ${state.name.value.replace(" ", "+")}"
            state.webview.value?.loadUrl(state.source.value)
        }
    }
}

