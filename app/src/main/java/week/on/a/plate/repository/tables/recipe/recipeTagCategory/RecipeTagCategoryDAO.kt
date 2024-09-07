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
    @Query("SELECT * FROM RecipeTagCategoryRoom")
    fun getAll(): Flow<List<RecipeTagCategoryRoom>>

    @Transaction
    @Query("SELECT * FROM RecipeTagCategoryRoom WHERE recipeTagCategoryId=:recipeTagCategoryId")
    fun getRecipeTagCategoryAndRecipeTag(recipeTagCategoryId:Long): Flow<RecipeTagCategoryAndRecipeTag>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeTagCategoryRoom: RecipeTagCategoryRoom)

    @Update
    suspend fun update(recipeTagCategoryRoom: RecipeTagCategoryRoom)

    @Delete
    suspend fun delete(recipeTagCategoryRoom: RecipeTagCategoryRoom)
}
