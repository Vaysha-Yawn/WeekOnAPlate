package week.on.a.plate.data.repository.room.cookPlanner


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CookPlannerStepDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CookPlannerStepRoom): Long

    @Update
    suspend fun update(item: CookPlannerStepRoom)

    @Query("DELETE FROM CookPlannerStepRoom WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM CookPlannerStepRoom WHERE plannerGroupId = :idGroup")
    suspend fun deleteByIdGroup(idGroup: Long)

    @Query("SELECT * FROM CookPlannerStepRoom  WHERE id = :id")
    suspend fun getById(id: Long): CookPlannerStepRoom

    @Query("SELECT * FROM CookPlannerStepRoom  WHERE originalStepId = :stepId")
    suspend fun getAllByOriginalStepId(stepId: Long): List<CookPlannerStepRoom>

    @Query("SELECT * FROM CookPlannerStepRoom  WHERE plannerGroupId = :plannerGroupId")
    suspend fun getByGroupId(plannerGroupId: Long): List<CookPlannerStepRoom>

    @Query("SELECT * FROM CookPlannerStepRoom  WHERE plannerGroupId = :plannerGroupId")
    fun getByGroupIdFlow(plannerGroupId: Long): Flow<List<CookPlannerStepRoom>>
}
