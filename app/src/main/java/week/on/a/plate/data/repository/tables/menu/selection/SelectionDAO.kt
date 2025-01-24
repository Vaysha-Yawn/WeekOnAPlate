package week.on.a.plate.data.repository.tables.menu.selection


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


@Dao
interface SelectionDAO {
    @Query("SELECT * FROM selectionroom")
    suspend fun getAll(): List<SelectionRoom>

    @Query("SELECT * FROM selectionroom WHERE id=:selectionId")
    suspend fun findSelection(selectionId:Long): SelectionRoom

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectionRoom: SelectionRoom):Long

    @Update
    suspend fun update(selectionRoom: SelectionRoom)

    @Query("DELETE FROM selectionroom WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM selectionroom WHERE weekOfYear=:week AND isForWeek==1")
    suspend fun findSelectionForWeek(week: Int) : SelectionRoom?

    @Query("SELECT * FROM selectionroom WHERE weekOfYear=:week AND isForWeek==1")
    fun findSelectionForWeekFlow(week: Int) : Flow<SelectionRoom?>

    @Query("SELECT * FROM selectionroom WHERE DATE(dateTime) = :day AND isForWeek==0")
    suspend fun findSelectionsForDay(day: String) : List<SelectionRoom>

    @Query("SELECT * FROM selectionroom WHERE DATE(dateTime) = :day AND isForWeek==0")
    fun findSelectionsForDayFlow(day: String) : Flow<List<SelectionRoom>>

    @Query("SELECT * FROM selectionroom WHERE DATE(dateTime) = :day AND isForWeek==0 AND name=:name")
    suspend fun findSelectionForDayByName(day: String, name:String) : SelectionRoom?
}
