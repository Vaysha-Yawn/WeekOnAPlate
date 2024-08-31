package week.on.a.plate.repository.tables.weekOrg.week

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuMapper
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionMapper
import javax.inject.Singleton

@Singleton
class WeekRepository(val dao: WeekDAO) {

    fun read(): Flow<List<WeekRoom>> = dao.getAll()

    fun findWeek(weekId:Long): Flow<WeekRoom> = dao.findWeek(weekId)

    fun getWeekAndSelection(weekId:Long): Flow<SelectionAndWeek> = dao.getWeekAndSelection(weekId)

    fun getWeekAndDay(weekId:Long): Flow<WeekAndDays> = dao.getWeekAndDay(weekId)

    suspend fun insert(week: WeekView) {
        val selRoom = with(SelectionMapper()) {
            week.selection.viewToRoom(0)
        }
        val selId = dao.insert(selRoom)

        val weekRoom = with(WeekMapper()) {
            week.viewToRoom(selId)
        }
        val weekId = dao.insert(weekRoom)

        week.days.forEach { day ->
            val dayRoom = with(DayMapper()) {
                day.viewToRoom(weekId)
            }
            val dayId = dao.insert(dayRoom)

        }
    }


    suspend fun update(week: WeekRoom) {
        dao.update(week)
    }

    suspend fun delete(week: WeekRoom) {
        dao.delete(week)
    }


}




