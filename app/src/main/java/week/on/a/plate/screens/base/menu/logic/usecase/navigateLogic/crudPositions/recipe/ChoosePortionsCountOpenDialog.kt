package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import javax.inject.Inject

class ChoosePortionsCountOpenDialog @Inject constructor() {

    suspend operator fun invoke(
        context: Context,
        mainViewModel: MainViewModel,
        use: suspend (Int) -> Unit
    ) = coroutineScope {
        val std = PreferenceUseCase().getDefaultPortionsCount(context)
        ChangePortionsCountViewModel.launch(mainViewModel, std) { count ->
            launch(Dispatchers.IO) {
                use(count)
            }
        }
    }
}