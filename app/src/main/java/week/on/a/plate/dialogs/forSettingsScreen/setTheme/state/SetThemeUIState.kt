package week.on.a.plate.dialogs.forSettingsScreen.setTheme.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.theme.palettes

class SetThemeUIState{
    val themes = palettes
    val selectedInd: MutableState<Int> = mutableIntStateOf(0)
}


