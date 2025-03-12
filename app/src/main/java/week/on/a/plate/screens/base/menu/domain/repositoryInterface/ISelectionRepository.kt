package week.on.a.plate.screens.base.menu.domain.repositoryInterface

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

//WeekMenuRepository
interface ISelectionRepository {
    fun getCurrentWeekFlow(
        nonPosedFullName: String,
        forWeekFullName: String,
        date: LocalDate,
        locale: Locale
    ): Flow<WeekView>

    suspend fun createSelection(
        date: LocalDate,
        newName: String,
        locale: Locale,
        isForWeek: Boolean,
        time: LocalTime
    )

    suspend fun deleteSelection(
        sel: SelectionView
    )

    suspend fun editSelection(
        sel: SelectionView,
        newName: String,
        time: LocalTime
    )

    suspend fun getSelIdOrCreate(
        dateTime: LocalDateTime,
        isForWeek: Boolean,
        category: String,
        locale: Locale
    ): Long
}