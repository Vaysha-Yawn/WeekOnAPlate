package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.recipe

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import javax.inject.Inject

class ChoosePortionsCountOpenDialog @Inject constructor() {

    suspend operator fun invoke(
        context: Context,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
        use: suspend (Int) -> Unit
    ) = coroutineScope {
        val std = PreferenceUseCase.getDefaultPortionsCount(context)
        val params = ChangePortionsCountViewModel.ChangePortionsCountDialogParams(std) { count ->
            scope.launch(Dispatchers.IO) {
                use(count)
            }
        }
        dialogOpenParams.value = params
    }
}