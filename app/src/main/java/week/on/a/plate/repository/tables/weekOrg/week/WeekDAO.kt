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
    fun getAll(): Flow<List<WeekRoom>>

    @Query("SELECT * FROM weekroom WHERE weekId=:weekId")
    fun findWeek(weekId:Long): Flow<WeekRoom>

    @Transaction
    @Query("SELECT * FROM weekroom WHERE weekId=:weekId")
    fun getWeekAndSelection(weekId:Long): Flow<SelectionAndWeek>

    @Transaction
    @Query("SELECT * FROM weekroom WHERE weekId=:weekId")
    fun getWeekAndDay(weekId:Long): Flow<WeekAndDays>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weekRoom: WeekRoom):Long

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selection: SelectionRoom):Long

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(day: DayRoom):Long

    @Update
    suspend fun update(weekRoom: WeekRoom)

    @Delete
    suspend fun delete(weekRoom: WeekRoom)
}
