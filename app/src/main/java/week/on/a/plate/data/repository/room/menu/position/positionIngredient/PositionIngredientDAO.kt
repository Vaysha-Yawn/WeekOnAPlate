package week.on.a.plate.data.repository.room.menu.position.positionIngredient


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeRoom


@Dao
interface PositionIngredientDAO {
    @Query("SELECT * FROM PositionIngredientRoom")
    suspend fun getAll(): List<PositionIngredientRoom>

    @Transaction
    @Query("SELECT * FROM IngredientInRecipeRoom WHERE id=:ingredientInRecipeId")
    suspend fun getPositionIngredientAndIngredientInRecipeView(ingredientInRecipeId: Long): PositionIngredientAndIngredientInRecipe

    @Query("SELECT * FROM PositionIngredientRoom WHERE selectionId=:selectionId")
    suspend fun getAllInSel(selectionId: Long): List<PositionIngredientRoom>

    @Query("SELECT * FROM PositionIngredientRoom WHERE selectionId=:selectionId")
    fun getAllInSelFlow(selectionId: Long): Flow<List<PositionIngredientRoom>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredientInRecipeRoom: IngredientInRecipeRoom): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positionIngredientRoom: PositionIngredientRoom): Long

    @Update
    suspend fun update(positionIngredientRoom: PositionIngredientRoom)

    @Query("DELETE FROM PositionIngredientRoom WHERE positionIngredientId = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM PositionIngredientRoom WHERE ingredientInRecipeId = :id")
    suspend fun deleteAllByIngredientInRecipeId(id: Long)

    @Query("SELECT * FROM PositionIngredientRoom WHERE positionIngredientId=:id")
    suspend fun getById(id: Long): PositionIngredientRoom
}
