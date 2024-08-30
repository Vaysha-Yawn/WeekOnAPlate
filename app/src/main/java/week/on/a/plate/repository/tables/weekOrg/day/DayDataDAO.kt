package week.on.a.plate.repository.tables.weekOrg.day


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface DayDataDAO {
    @Query("SELECT * FROM daydata")
    fun getAll(): Flow<List<DayData>>

    @Query("SELECT * FROM daydata WHERE date=:date")
    fun findDay(date:Date): Flow<DayData>

    @Transaction
    @Query("SELECT * FROM daydata WHERE dayId=:dayId")
    fun getDayAndSelection(dayId:Long): Flow<DayAndSelections>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dayData: DayData)

    @Update
    suspend fun update(dayData: DayData)

    @Delete
    suspend fun delete(dayData: DayData)
}
