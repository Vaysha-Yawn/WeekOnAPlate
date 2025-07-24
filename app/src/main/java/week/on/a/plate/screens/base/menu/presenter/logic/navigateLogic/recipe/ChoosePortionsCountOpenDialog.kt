package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.recipe

import android.content.Context
import androidx.compose.runtime.MutableState
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import javax.inject.Inject

//todo find code like this and replace to this code

class ChoosePortionsCountOpenDialog @Inject constructor() {
    operator fun invoke(
        startCount: Int,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        use: suspend (Int) -> Unit
    ) {
        val params =
            ChangePortionsCountViewModel.ChangePortionsCountDialogParams(startCount) { count ->
                use(count)
            }
        dialogOpenParams.value = params
    }

    operator fun invoke(
        context: Context,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        use: suspend (Int) -> Unit
    ) {
        val std = PreferenceUseCase.getDefaultPortionsCount(context)
        val params = ChangePortionsCountViewModel.ChangePortionsCountDialogParams(std) { count ->
                use(count)
        }
        dialogOpenParams.value = params
    }
}