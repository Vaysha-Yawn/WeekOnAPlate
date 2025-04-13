package week.on.a.plate.screens.additional.deleteApply.navigation

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent


@Serializable
data object DeleteApplyDestination

class DeleteApplyNavParams(
    private val context: Context,
    private val title: String? = null,
    private val message: String,
    private val use: suspend (DeleteApplyEvent) -> Unit
) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.viewModelScope.launch {
            vm.deleteApplyViewModel.launchAndGet(context, title, message, use)
        }
    }
}