package week.on.a.plate.screens.base.menu.logic.usecase.dbusecase

import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.repository.tables.menu.selection.WeekMenuRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

class AddSelectionToDBUseCase @Inject constructor(
    private val menuR: WeekMenuRepository
) {
    suspend operator fun invoke(
        date: LocalDate, newName: String, locale: Locale,
        isForWeek: Boolean, time: LocalTime
    ) {
        menuR.createSelection(
            date, newName, locale,
            isForWeek, time
        )
    }
}

class DeleteSelectionInDBUseCase @Inject constructor(
    private val menuR: WeekMenuRepository
) {
    suspend operator fun invoke(sel: SelectionView) {
        menuR.deleteSelection(sel)
    }
}

class EditSelectionInDBUseCase @Inject constructor(
    private val menuR: WeekMenuRepository
) {
    suspend operator fun invoke(sel: SelectionView, newName: String, time: LocalTime) {
        menuR.editSelection(
            sel, newName, time
        )
    }
}

class GetSelOrCreateInDBUseCase @Inject constructor(
    private val menuR: WeekMenuRepository
) {
    suspend operator fun invoke(
        dateTime: LocalDateTime,
        isForWeek: Boolean,
        category: String,
        locale: Locale
    ) {
        menuR.getSelIdOrCreate(dateTime, isForWeek, category, locale)
    }
}