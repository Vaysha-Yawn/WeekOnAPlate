package week.on.a.plate.repository.tables.weekOrg.day

import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Singleton

@Singleton
class DayDataRepository(val dao: DayDataDAO) {

    fun read(): Flow<List<DayData>> = dao.getAll()

    fun findDay(date:Date): Flow<DayData> = dao.findDay(date)

    fun getDayAndSelection(dayId:Long): Flow<DayAndSelections> = dao.getDayAndSelection(dayId)

    suspend fun create(day: DayData) {
        dao.insert(day)
    }

    suspend fun update(day: DayData) {
        dao.update(day)
    }

    suspend fun delete(day: DayData) {
        dao.delete(day)
    }

}




