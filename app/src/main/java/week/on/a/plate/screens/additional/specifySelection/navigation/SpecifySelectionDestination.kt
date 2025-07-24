package week.on.a.plate.screens.additional.specifySelection.navigation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import week.on.a.plate.core.navigation.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionResult

@Serializable
data object SpecifySelectionDestination