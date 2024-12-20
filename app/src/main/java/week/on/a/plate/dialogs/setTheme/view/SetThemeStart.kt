package week.on.a.plate.dialogs.setTheme.view

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorPaletteGroup
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.dialogs.setTheme.event.SetThemeEvent
import week.on.a.plate.dialogs.setTheme.state.SetThemeUIState
import week.on.a.plate.screens.filters.view.clickNoRipple


@Composable
fun SetThemeStart(
    state: SetThemeUIState,
    onEvent: (SetThemeEvent) -> Unit
) {
    LazyRow (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface).fillMaxWidth()
            .padding(vertical = 24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        itemsIndexed(state.themes){ind, theme ->
            ThemeCard(ind, theme, onEvent)
            Spacer(Modifier.size(24.dp))
        }
    }
}

@Composable
fun getContext(): ComponentActivity? {
    var context = LocalContext.current
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext as ContextWrapper
    }
    return null
}

@Composable
fun ThemeCard(ind:Int, palette: ColorPaletteGroup, onEvent: (SetThemeEvent) -> Unit) {
    val colorSchemeLight = palette.getColorScheme(false)
    val colorSchemeDark = palette.getColorScheme(true)
    val context = getContext()

    Column(Modifier.clickNoRipple {
        if (context == null) onEvent(SetThemeEvent.Close)
        else onEvent(SetThemeEvent.Select(ind, context))
    }, horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.clip(RoundedCornerShape(6.dp))) {
            Row(Modifier.background(colorSchemeLight.surface).padding(horizontal = 6.dp, vertical = 6.dp)) {
                Row(Modifier.background(colorSchemeLight.background, RoundedCornerShape(6.dp)).padding(horizontal = 6.dp, vertical = 6.dp)) {
                    TextBody("S", Modifier.background(colorSchemeLight.primary, CircleShape).padding(horizontal = 6.dp), color = colorSchemeLight.onBackground)
                    Spacer(Modifier.width(12.dp))
                    TextBody("S", Modifier.background(colorSchemeLight.secondary, RoundedCornerShape(6.dp)).padding(horizontal = 6.dp), color = colorSchemeLight.onBackground)
                }
            }
            Row(Modifier.background(colorSchemeDark.surface).padding(horizontal = 6.dp, vertical = 6.dp)) {
                Row(Modifier.background(colorSchemeDark.background, RoundedCornerShape(6.dp)).padding(horizontal = 6.dp, vertical = 6.dp)) {
                    TextBody("S", Modifier.background(colorSchemeDark.primary, CircleShape).padding(horizontal = 6.dp), color = colorSchemeDark.onBackground)
                    Spacer(Modifier.width(12.dp))
                    TextBody("S", Modifier.background(colorSchemeDark.secondary, RoundedCornerShape(6.dp)).padding(horizontal = 6.dp), color = colorSchemeDark.onBackground)
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        TextTitle(stringResource(palette.name) )
        Spacer(Modifier.height(12.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSetTheme() {
    WeekOnAPlateTheme {
        SetThemeStart(SetThemeUIState()) {}
    }
}