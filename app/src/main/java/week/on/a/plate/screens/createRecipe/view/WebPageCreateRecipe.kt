package week.on.a.plate.screens.createRecipe.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.buttons.DoneButtonSmall
import week.on.a.plate.core.uitools.buttons.TextButton
import week.on.a.plate.core.uitools.webview.WebPage
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.filters.view.clickNoRipple
import java.util.Locale

@Composable
fun WebPageCreateRecipe(state: RecipeCreateUIState, onEvent: (Event) -> Unit) {
    val context = LocalContext.current
    val countryCode = remember{Locale.getDefault().language}
    if (state.source.value == "") {
        DoneButtonSmall(text = stringResource(R.string.search_be_name_recipe_in_internet), Modifier.padding(24.dp)) {
            state.source.value =
                "https://www.google.$countryCode/search?q="+context.getString(R.string.recipe)+"+"+ (state.name.value.replace(" ", "+"))
        }
    } else {
        WebPage(url = state.source, state.webview, onEvent, true)
    }
}



@Composable
fun RowWebActions(state: RecipeCreateUIState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBtn(Icons.Rounded.ArrowBack) {
            if (state.webview.value?.canGoBack() == true) {
                state.webview.value?.goBack()
            }
        }
        Spacer(Modifier.width(10.dp))
        IconBtn(Icons.Rounded.ArrowForward) {
            if (state.webview.value?.canGoForward() == true) {
                state.webview.value?.goForward()
            }
        }
        Spacer(Modifier.width(10.dp))
        IconBtn(Icons.Rounded.Refresh) {
            state.webview.value?.reload()
        }
        Spacer(Modifier.width(10.dp))
        val context = LocalContext.current
        TextButton(stringResource(R.string.search_again)) {
            state.source.value =
                "https://www.google.ru/search?q="+context.getString(R.string.recipe)+ {state.name.value.replace(" ", "+")}.toString()
            state.webview.value?.loadUrl(state.source.value)
        }
    }
}

@Composable
fun IconBtn(icon: ImageVector, click: () -> Unit) {
    Icon(
        icon,
        contentDescription = "",
        modifier = Modifier
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp))
            .clickNoRipple {
                click()
            },
        tint = MaterialTheme.colorScheme.onBackground
    )
}

