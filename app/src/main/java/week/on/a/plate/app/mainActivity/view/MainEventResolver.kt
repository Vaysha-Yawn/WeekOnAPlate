package week.on.a.plate.app.mainActivity.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams


@Composable
fun MainEventResolve(
    mainEvent: MutableState<MainEvent?>,
    dialogOpenParams: MutableState<DialogOpenParams?>,
    mainVM: MainViewModel = hiltViewModel()
) {
    //nav
    LaunchedEffect(mainEvent.value) {
        if (mainEvent.value != null) {
            mainVM.onEvent(mainEvent.value!!)
            mainEvent.value = null
        }
    }

    //openDialog
    LaunchedEffect(dialogOpenParams.value) {
        if (dialogOpenParams.value != null) {
            dialogOpenParams.value!!.openDialog(mainVM)
            dialogOpenParams.value = null
        }
    }
}