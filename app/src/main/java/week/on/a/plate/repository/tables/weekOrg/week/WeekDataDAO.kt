package week.on.a.plate.repository.tables.weekOrg.week


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface WeekDataDAO {
    @Query("SELECT * FROM weekdata")
    fun getAll(): Flow<List<WeekData>>

    @Transaction
    @Query("SELECT * FROM weekdata WHERE weekId=:weekId")
    fun getWeekAndSelection(weekId:Long): Flow<List<SelectionAndWeek>>

    @Transaction
    @Query("SELECT * FROM weekdata WHERE weekId=:weekId")
    fun getWeekAndDay(weekId:Long): Flow<List<WeekAndDays>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weekData: WeekData)

    @Update
    suspend fun update(weekData: WeekData)

    @Delete
    suspend fun delete(weekData: WeekData)
}
