package week.on.a.plate.data.repository.tables.menu.position.draft


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.repository.tables.menu.position.draft.draftIngredientCrossRef.DraftAndIngredient
import week.on.a.plate.data.repository.tables.menu.position.draft.draftIngredientCrossRef.DraftAndIngredientCrossRef
import week.on.a.plate.data.repository.tables.menu.position.draft.draftTagCrossRef.DraftAndTag
import week.on.a.plate.data.repository.tables.menu.position.draft.draftTagCrossRef.DraftAndTagCrossRef


@Dao
interface PositionDraftDAO {
    @Query("SELECT * FROM PositionDraftRoom")
    suspend fun getAll(): List<PositionDraftRoom>

    @Query("SELECT * FROM PositionDraftRoom WHERE selectionId=:selectionId")
    suspend fun getAllInSel(selectionId:Long): List<PositionDraftRoom>


    @Query("SELECT * FROM PositionDraftRoom WHERE selectionId=:selectionId")
    fun getAllInSelFlow(selectionId:Long): Flow<List<PositionDraftRoom>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positionDraft: PositionDraftRoom):Long

    @Transaction
    @Query("SELECT * FROM PositionDraftRoom WHERE draftId =:draftId")
    suspend fun getDraftAndTagByDraftId(draftId:Long): DraftAndTag

    @Transaction
    @Query("SELECT * FROM PositionDraftRoom WHERE draftId =:draftId")
    suspend fun getDraftAndIngredientByDraftId(draftId:Long): DraftAndIngredient


    @Query("SELECT * FROM DraftAndTagCrossRef WHERE draftId =:draftId")
    fun getDraftAndTagByDraftIdFlow(draftId:Long): Flow<List<DraftAndTagCrossRef>>

    @Query("SELECT * FROM DraftAndIngredientCrossRef WHERE draftId =:draftId")
    fun getDraftAndIngredientByDraftIdFlow(draftId:Long): Flow<List<DraftAndIngredientCrossRef>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraftAndIngredientCrossRef(draftAndIngredientCrossRefs: DraftAndIngredientCrossRef)

    @Query("DELETE FROM DraftAndIngredientCrossRef WHERE ingredientId = :id")
    suspend fun deleteByIdIngredient(id: Long)

    @Query("DELETE FROM DraftAndTagCrossRef WHERE recipeTagId = :id")
    suspend fun deleteByIdTag(id: Long)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraftAndTagCrossRef(draftAndTagCrossRefs: DraftAndTagCrossRef)

    @Update
    suspend fun update(positionDraft: PositionDraftRoom)

    @Query("DELETE FROM PositionDraftRoom WHERE draftId = :id")
    suspend fun deleteById(id: Long)
}
