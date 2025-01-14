package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.state.MenuUIState
import javax.inject.Inject

class UseCase @Inject constructor(

) {
    operator fun invoke(
        mainViewModel: MainViewModel,
        updateWeek: () -> Unit,
        menuUIState: MenuUIState
    ) {

    }
}