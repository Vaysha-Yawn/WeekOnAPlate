package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.recipe

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import javax.inject.Inject

class ChoosePortionsCountOpenDialog @Inject constructor() {

    operator fun invoke(
        context: Context,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
        use: suspend (Int) -> Unit
    ) {
        val std = PreferenceUseCase.getDefaultPortionsCount(context)
        val params = ChangePortionsCountViewModel.ChangePortionsCountDialogParams(std) { count ->
                use(count)
        }
        dialogOpenParams.value = params
    }
}