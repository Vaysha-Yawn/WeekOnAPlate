package week.on.a.plate.data.repository.tables.menu.category_selection


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CategorySelectionDAO {
    @Query("SELECT * FROM CategorySelectionRoom")
    suspend fun getAll(): List<CategorySelectionRoom>

    @Query("SELECT * FROM CategorySelectionRoom")
    fun getAllFlow(): Flow<List<CategorySelectionRoom>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectionRoom: CategorySelectionRoom):Long

    @Update
    suspend fun update(selectionRoom: CategorySelectionRoom)

    @Query("DELETE FROM CategorySelectionRoom WHERE id = :id")
    suspend fun deleteById(id: Long)
}
