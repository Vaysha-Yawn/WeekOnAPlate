package week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeRecipeTagCrossRefDAO {
    @Query("SELECT * FROM reciperecipetagcrossref")
    fun getAll(): Flow<List<RecipeRecipeTagCrossRef>>

    @Transaction
    @Query("SELECT * FROM Recipe")
    fun getRecipeAndRecipeTag(): Flow<List<RecipeAndRecipeTag>>

    @Transaction
    @Query("SELECT * FROM RecipeTag")
    fun getRecipeTagAndRecipe(): Flow<List<RecipeTagAndRecipe>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeRecipeTagCrossRef: RecipeRecipeTagCrossRef)

    @Update
    suspend fun update(recipeRecipeTagCrossRef: RecipeRecipeTagCrossRef)

    @Delete
    suspend fun delete(recipeRecipeTagCrossRef: RecipeRecipeTagCrossRef)
}
