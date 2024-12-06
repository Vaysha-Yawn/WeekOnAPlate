package week.on.a.plate.data.repository.tables.cookPlanner


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CookPlannerGroupDAO {
    @Query("SELECT * FROM CookPlannerGroupRoom WHERE DATE(start) = :date")
    fun getAllFlowByDateStart(date: String): Flow<List<CookPlannerGroupRoom>>

    @Query("SELECT * FROM CookPlannerGroupRoom WHERE DATE(start) = :date")
    suspend fun getAllByDateStart(date: String): List<CookPlannerGroupRoom>

    @Query("SELECT * FROM CookPlannerGroupRoom")
    fun getAllFlow(): Flow<List<CookPlannerGroupRoom>>

    @Query("SELECT * FROM CookPlannerGroupRoom")
    suspend fun getAll(): List<CookPlannerGroupRoom>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CookPlannerGroupRoom):Long

    @Update
    suspend fun update(item: CookPlannerGroupRoom)

    @Query("DELETE FROM CookPlannerGroupRoom WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM CookPlannerGroupRoom  WHERE id = :id")
    suspend fun getById(id: Long) : CookPlannerGroupRoom

    @Query("SELECT * FROM CookPlannerGroupRoom  WHERE recipeId = :id")
    suspend fun getAllByRecipeId(id: Long):List<CookPlannerGroupRoom>
}
