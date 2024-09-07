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
    @Query("SELECT * FROM recipeRoom")
    fun getAll(): Flow<List<RecipeRoom>>

    @Transaction
    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    fun getRecipeAndRecipeSteps(recipeId:Long): Flow<RecipeAndRecipeSteps>

    @Transaction
    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    fun getRecipeAndIngredientInRecipe(recipeId:Long): Flow<RecipeAndIngredientInRecipe>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeRoom: RecipeRoom)

    @Update
    suspend fun update(recipeRoom: RecipeRoom)

    @Delete
    suspend fun delete(recipeRoom: RecipeRoom)
}
