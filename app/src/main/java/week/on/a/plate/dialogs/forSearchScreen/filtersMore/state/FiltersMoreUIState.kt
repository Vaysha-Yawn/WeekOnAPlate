package week.on.a.plate.dialogs.forSearchScreen.filtersMore.state

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class FiltersMoreUIState(){
    val favoriteIsChecked = mutableStateOf(false)
    val allTime = mutableIntStateOf(0)
}


