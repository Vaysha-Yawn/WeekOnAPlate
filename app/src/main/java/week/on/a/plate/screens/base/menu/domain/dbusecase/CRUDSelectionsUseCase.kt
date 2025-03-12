package week.on.a.plate.screens.base.menu.domain.dbusecase


import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screens.base.menu.domain.repositoryInterface.ISelectionRepository
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

class GetWeekFlowUseCase @Inject constructor(
    private val menuR: ISelectionRepository
) {
    operator fun invoke(
        nonPosedFullName: String,
        forWeekFullName: String,
        date: LocalDate,
        locale: Locale
    ): Flow<WeekView> {
        return menuR.getCurrentWeekFlow(
            nonPosedFullName, forWeekFullName, date, locale
        )
    }
}

class AddSelectionToDBUseCase @Inject constructor(
    private val menuR: ISelectionRepository
) {
    suspend operator fun invoke(
        date: LocalDate, newName: String, locale: Locale,
        isForWeek: Boolean, time: LocalTime
    ) = coroutineScope {
        launch(Dispatchers.IO) {
            menuR.createSelection(
                date, newName, locale,
                isForWeek, time
            )
        }
    }
}

class DeleteSelectionInDBUseCase @Inject constructor(
    private val menuR: ISelectionRepository
) {
    suspend operator fun invoke(sel: SelectionView) = coroutineScope {
        launch(Dispatchers.IO) {
            menuR.deleteSelection(sel)
        }
    }
}

class EditSelectionInDBUseCase @Inject constructor(
    private val menuR: ISelectionRepository
) {
    suspend operator fun invoke(sel: SelectionView, newName: String, time: LocalTime) =
        coroutineScope {
            launch(Dispatchers.IO) {
                menuR.editSelection(
                    sel, newName, time
                )
            }
    }
}

class GetSelOrCreateInDBUseCase @Inject constructor(
    private val menuR: ISelectionRepository
) {
    suspend operator fun invoke(
        dateTime: LocalDateTime,
        isForWeek: Boolean,
        category: String,
        locale: Locale
    ): Long = coroutineScope {
        menuR.getSelIdOrCreate(dateTime, isForWeek, category, locale)
    }
}

class CreateFirstNonPosedPositionUseCase @Inject constructor(
    private val menuR: ISelectionRepository,
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