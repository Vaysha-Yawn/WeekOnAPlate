package week.on.a.plate.repository.tables.weekOrg.selectionInDay


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface SelectionInDayDAO {
    @Query("SELECT * FROM selectioninday")
    fun getAll(): Flow<List<SelectionInDay>>

    @Transaction
    @Query("SELECT * FROM selectioninday WHERE selectionId=:selectionId")
    fun getSelectionAndRecipesInMenu(selectionId:Long): Flow<List<SelectionAndRecipesInMenu>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectionInDay: SelectionInDay)

    @Update
    suspend fun update(selectionInDay: SelectionInDay)

    @Delete
    suspend fun delete(selectionInDay: SelectionInDay)
}
