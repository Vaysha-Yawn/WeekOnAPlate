package week.on.a.plate.data.repository.tables.menu.selection


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

    @Delete
    suspend fun delete(selectionRoom: SelectionRoom)

    @Query("SELECT * FROM selectionroom WHERE weekOfYear=:week AND isForWeek==1")
    suspend fun findSelectionForWeek(week: Int) : SelectionRoom?

    @Query("SELECT * FROM selectionroom WHERE date=:day AND isForWeek==0")
    suspend fun findSelectionsForDay(day: LocalDate) : List<SelectionRoom>

    @Query("SELECT * FROM selectionroom WHERE date=:day AND isForWeek==0 AND name=:name")
    suspend fun findSelectionForDayByName(day: LocalDate, name:String) : SelectionRoom?
}
