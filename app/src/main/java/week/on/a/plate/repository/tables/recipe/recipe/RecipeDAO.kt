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
    suspend fun getAll(): List<RecipeRoom>

    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    suspend fun getRecipeById(recipeId:Long): RecipeRoom

    @Transaction
    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    suspend fun getRecipeAndRecipeSteps(recipeId:Long): RecipeAndRecipeSteps

    @Transaction
    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    suspend fun getRecipeAndIngredientInRecipe(recipeId:Long): RecipeAndIngredientInRecipe

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeRoom: RecipeRoom):Long

    @Update
    suspend fun update(recipeRoom: RecipeRoom)

    @Query("DELETE FROM recipeRoom WHERE recipeId = :recipeIdd")
    suspend fun deleteById(recipeIdd: Long)
}
