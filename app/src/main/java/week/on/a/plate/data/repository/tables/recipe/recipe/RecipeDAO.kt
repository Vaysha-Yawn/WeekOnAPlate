package week.on.a.plate.data.repository.tables.recipe.recipe


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom
import week.on.a.plate.data.repository.tables.recipe.recipeStep.RecipeStepRoom


@Dao
interface RecipeDAO {
    @Query("SELECT * FROM recipeRoom")
    suspend fun getAll(): List<RecipeRoom>

    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    suspend fun getRecipeById(recipeId:Long): RecipeRoom

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeRoom):Long

    @Update
    suspend fun update(recipeRoom: RecipeRoom)

    @Query("DELETE FROM recipeRoom WHERE recipeId = :recipeIdd")
    suspend fun deleteById(recipeIdd: Long)
}
