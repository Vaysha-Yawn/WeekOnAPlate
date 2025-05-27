package week.on.a.plate.screens.additional.ppAndTermsOfUse.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel

@Serializable
data object DocumentsWebDestination

class DocumentsWebNavParams(private val isForPP: Boolean) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.documentsWebViewModel.launch(isForPP)
    }

}