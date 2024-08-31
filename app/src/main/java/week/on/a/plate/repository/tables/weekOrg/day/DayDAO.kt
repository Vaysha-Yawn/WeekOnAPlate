package week.on.a.plate.repository.tables.weekOrg.day


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionRoom
import java.time.LocalDate


@Dao
interface DayDAO {
    @Query("SELECT * FROM dayroom")
    suspend fun getAll(): List<DayRoom>

    @Query("SELECT * FROM dayroom WHERE date=:date")
    suspend fun findDay(date:LocalDate): DayRoom?

    @Transaction
    @Query("SELECT * FROM dayroom WHERE dayId=:dayId")
    suspend fun getDayAndSelection(dayId:Long): DayAndSelections

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dayRoom: DayRoom):Long

    @Update
    suspend fun update(dayRoom: DayRoom)

    @Delete
    suspend fun delete(dayRoom: DayRoom)
}
