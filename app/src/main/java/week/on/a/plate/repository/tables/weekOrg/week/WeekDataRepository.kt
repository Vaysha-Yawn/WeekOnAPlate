package week.on.a.plate.repository.tables.weekOrg.week

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class WeekDataRepository(val dao: WeekDataDAO) {

    fun read(): Flow<List<WeekData>> = dao.getAll()

    fun findWeek(weekId:Long): Flow<WeekData> = dao.findWeek(weekId)

    fun getWeekAndSelection(weekId:Long): Flow<SelectionAndWeek> = dao.getWeekAndSelection(weekId)

    fun getWeekAndDay(weekId:Long): Flow<WeekAndDays> = dao.getWeekAndDay(weekId)

    suspend fun create(week: WeekData) {
        dao.insert(week)
    }

    suspend fun update(week: WeekData) {
        dao.update(week)
    }

    suspend fun delete(week: WeekData) {
        dao.delete(week)
    }


}




