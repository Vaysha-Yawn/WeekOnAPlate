package week.on.a.plate.repository.tables.weekOrg.position.positionDraft


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef.DraftAndIngredient
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef.DraftAndIngredientCrossRef
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftTagCrossRef.DraftAndTag
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftTagCrossRef.DraftAndTagCrossRef
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRoom


@Dao
interface PositionDraftDAO {
    @Query("SELECT * FROM PositionDraftRoom")
    suspend fun getAll(): List<PositionDraftRoom>

    @Query("SELECT * FROM PositionDraftRoom WHERE selectionId=:selectionId")
    suspend fun getAllInSel(selectionId:Long): List<PositionDraftRoom>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positionDraft: PositionDraftRoom):Long

    @Transaction
    @Query("SELECT * FROM PositionDraftRoom WHERE draftId =:draftId")
    suspend fun getDraftAndTagByDraftId(draftId:Long): DraftAndTag

    @Transaction
    @Query("SELECT * FROM PositionDraftRoom WHERE draftId =:draftId")
    suspend fun getDraftAndIngredientByDraftId(draftId:Long): DraftAndIngredient

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraftAndIngredientCrossRef(draftAndIngredientCrossRefs: DraftAndIngredientCrossRef)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraftAndTagCrossRef(draftAndTagCrossRefs: DraftAndTagCrossRef)

    @Update
    suspend fun update(positionDraft: PositionDraftRoom)

    @Query("DELETE FROM PositionDraftRoom WHERE draftId = :id")
    suspend fun deleteById(id: Long)
}
