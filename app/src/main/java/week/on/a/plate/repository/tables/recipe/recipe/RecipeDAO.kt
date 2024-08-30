package week.on.a.plate.repository.tables.recipe.recipe


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDAO {
    @Query("SELECT * FROM recipe")
    fun getAll(): Flow<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM recipe WHERE recipeId=:recipeId")
    fun getRecipeAndRecipeSteps(recipeId:Long): Flow<List<RecipeAndRecipeSteps>>

    @Transaction
    @Query("SELECT * FROM recipe WHERE recipeId=:recipeId")
    fun getRecipeAndIngredientInRecipe(recipeId:Long): Flow<List<RecipeAndIngredientInRecipe>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)
}
