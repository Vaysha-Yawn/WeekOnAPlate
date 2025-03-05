package week.on.a.plate.screens.base.menu.logic.usecase

import android.content.Context
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.repository.tables.menu.selection.WeekMenuRepository
import week.on.a.plate.screens.base.menu.event.MenuEvent
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class CreateFirstNonPosedPositionUseCase @Inject constructor(
    private val menuR: WeekMenuRepository,
) {
    suspend operator fun invoke(
        date: LocalDate,
        selectionView: SelectionView,
        context: Context,
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit
    ) {
        val time = selectionView.dateTime.toLocalTime()
        val sel = menuR.getSelIdOrCreate(
            LocalDateTime.of(date, time),
            false,
            selectionView.name,
            mainViewModel.locale,
        )
        onEvent(MenuEvent.CreatePosition(sel, context))
    }
}