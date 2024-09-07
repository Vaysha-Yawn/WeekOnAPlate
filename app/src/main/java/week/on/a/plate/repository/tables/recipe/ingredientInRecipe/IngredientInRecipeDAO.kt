package week.on.a.plate.repository.tables.recipe.ingredientInRecipe


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientRoom


@Dao
interface IngredientInRecipeDAO {
    @Query("SELECT * FROM IngredientInRecipeRoom")
    fun getAll(): Flow<List<IngredientInRecipeRoom>>

    @Transaction
    @Query("SELECT * FROM IngredientRoom WHERE ingredientId=:ingredientId")
    fun getIngredientAndIngredientInRecipe(ingredientId:Long): Flow<IngredientAndIngredientInRecipe>

    @Query("SELECT * FROM IngredientRoom WHERE ingredientId = :ingredientId")
    suspend fun getCurrent(ingredientId: Long): IngredientRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: IngredientInRecipeRoom)

    @Update
    suspend fun update(recipe: IngredientInRecipeRoom)

    @Delete
    suspend fun delete(recipe: IngredientInRecipeRoom)
}
