package week.on.a.plate.data.repository.tables.recipe.recipeRecipeTagCrossRef


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
    suspend fun getAll(): List<RecipeRecipeTagCrossRef>

    @Transaction
    @Query("SELECT * FROM RecipeRoom")
    suspend fun getRecipeAndRecipeTag(): List<RecipeAndRecipeTag>

    @Transaction
    @Query("SELECT * FROM RecipeTagRoom")
    suspend fun getRecipeTagAndRecipe(): List<RecipeTagAndRecipe>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeRecipeTagCrossRef: RecipeRecipeTagCrossRef):Long

    @Update
    suspend fun update(recipeRecipeTagCrossRef: RecipeRecipeTagCrossRef)

    @Query("DELETE FROM reciperecipetagcrossref WHERE recipeId = :recipeIdd")
    suspend fun deleteById(recipeIdd: Long)
}
