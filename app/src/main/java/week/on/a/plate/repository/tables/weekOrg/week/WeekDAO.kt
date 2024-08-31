package week.on.a.plate.repository.tables.weekOrg.week


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.weekOrg.day.DayRoom
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionRoom


@Dao
interface WeekDAO {
    @Query("SELECT * FROM weekroom")
    suspend fun getAll(): List<WeekRoom>

    @Query("SELECT * FROM weekroom WHERE weekId=:weekId")
    suspend fun findWeek(weekId:Long): WeekRoom

    @Transaction
    @Query("SELECT * FROM weekroom WHERE weekId=:weekId")
    suspend fun getWeekAndSelection(weekId:Long): SelectionAndWeek

    @Transaction
    @Query("SELECT * FROM weekroom WHERE weekId=:weekId")
    suspend fun getWeekAndDay(weekId:Long): WeekAndDays

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weekRoom: WeekRoom):Long

    @Update
    suspend fun update(weekRoom: WeekRoom)

    @Delete
    suspend fun delete(weekRoom: WeekRoom)
}
