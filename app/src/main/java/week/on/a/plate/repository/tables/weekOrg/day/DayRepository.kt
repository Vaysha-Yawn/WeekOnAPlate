package week.on.a.plate.repository.tables.weekOrg.day

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionMapper
import java.time.LocalDate
import javax.inject.Singleton

@Singleton
class DayRepository(val dao: DayDAO) {

    fun read(): Flow<List<DayRoom>> = dao.getAll()

    fun findDay(date: LocalDate): Flow<DayRoom> = dao.findDay(date)

    fun getDayAndSelection(dayId: Long): Flow<DayAndSelections> = dao.getDayAndSelection(dayId)

    suspend fun insert(weekId: Long, day: DayView) {
        val dayRoom = with(DayMapper()) {
            day.viewToRoom(weekId)
        }
        dao.insert(dayRoom)

        //
        day.selections.forEach { sel ->
            val selRoom = with(SelectionMapper()) {
                sel.viewToRoom(day.id)
            }
            dao.insert(selRoom)
        }
    }


    suspend fun update(day: DayRoom) = dao.update(day)


    suspend fun delete(day: DayRoom) = dao.delete(day)


}




