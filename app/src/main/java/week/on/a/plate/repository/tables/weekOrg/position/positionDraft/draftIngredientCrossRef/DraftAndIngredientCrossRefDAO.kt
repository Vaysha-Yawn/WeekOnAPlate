package week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DraftAndIngredientCrossRefDAO {
    @Query("SELECT * FROM DraftAndIngredientCrossRef")
    fun getAll(): Flow<List<DraftAndIngredientCrossRef>>

    @Transaction
    @Query("SELECT * FROM PositionDraftRoom")
    fun getDraftAndIngredient(): Flow<List<DraftAndIngredient>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(draftAndIngredientCrossRef: DraftAndIngredientCrossRef)

    @Update
    suspend fun update(draftAndIngredientCrossRef: DraftAndIngredientCrossRef)

    @Delete
    suspend fun delete(draftAndIngredientCrossRef: DraftAndIngredientCrossRef)
}
