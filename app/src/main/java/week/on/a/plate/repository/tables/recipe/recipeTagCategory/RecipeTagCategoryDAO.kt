package week.on.a.plate.repository.tables.recipe.recipeTagCategory


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeTagCategoryDAO {
    @Query("SELECT * FROM recipeTagCategory")
    fun getAll(): Flow<List<RecipeTagCategory>>

    @Transaction
    @Query("SELECT * FROM recipeTagCategory WHERE recipeTagCategoryId=:recipeTagCategoryId")
    fun getRecipeTagCategoryAndRecipeTag(recipeTagCategoryId:Long): Flow<RecipeTagCategoryAndRecipeTag>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeTagCategory: RecipeTagCategory)

    @Update
    suspend fun update(recipeTagCategory: RecipeTagCategory)

    @Delete
    suspend fun delete(recipeTagCategory: RecipeTagCategory)
}
