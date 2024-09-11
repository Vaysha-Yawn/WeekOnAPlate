package week.on.a.plate.repository.tables.weekOrg.position.positionIngredient


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom


@Dao
interface PositionIngredientDAO {
    @Query("SELECT * FROM PositionIngredientRoom")
    suspend fun getAll(): List<PositionIngredientRoom>

    @Transaction
    @Query("SELECT * FROM IngredientInRecipeRoom WHERE id=:ingredientInRecipeId")
    suspend fun getPositionIngredientAndIngredientInRecipeView(ingredientInRecipeId:Long): PositionIngredientAndIngredientInRecipeView

    @Query("SELECT * FROM PositionIngredientRoom WHERE selectionId=:selectionId")
    fun getAllInSel(selectionId:Long): Flow<List<PositionIngredientRoom>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientInRecipeRoom: IngredientInRecipeRoom):Long

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positionIngredientRoom: PositionIngredientRoom):Long

    @Update
    suspend fun update(positionIngredientRoom: PositionIngredientRoom)

    @Query("DELETE FROM PositionIngredientRoom WHERE positionIngredientId = :id")
    suspend fun deleteById(id: Long)
}
