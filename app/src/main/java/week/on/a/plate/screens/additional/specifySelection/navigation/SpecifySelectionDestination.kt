package week.on.a.plate.screens.additional.specifySelection.navigation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionResult

@Serializable
data object SpecifySelectionDestination

class SpecifySelectionParams(private val use: (SpecifySelectionResult) -> Unit) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.viewModelScope.launch {
            vm.specifySelectionViewModel.launchAndGet(use, vm.menuViewModel)
        }
    }
}