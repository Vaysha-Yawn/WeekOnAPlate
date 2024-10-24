package week.on.a.plate.data.repository.tables.cookPlanner


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CookPlannerStepDAO {
    @Query("SELECT * FROM CookPlannerStepRoom WHERE DATE(start) = :date")
    fun getAllFlowByDateStart(date: String): Flow<List<CookPlannerStepRoom>>

    @Query("SELECT * FROM CookPlannerStepRoom WHERE DATE(start) = :date")
    suspend fun getAllFlowByDateStartNoFlow(date: String): List<CookPlannerStepRoom>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CookPlannerStepRoom):Long

    @Update
    suspend fun update(item: CookPlannerStepRoom)

    @Query("DELETE FROM CookPlannerStepRoom WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM CookPlannerStepRoom WHERE plannerGroupId = :idGroup")
    suspend fun deleteByIdGroup(idGroup: Long)
}
