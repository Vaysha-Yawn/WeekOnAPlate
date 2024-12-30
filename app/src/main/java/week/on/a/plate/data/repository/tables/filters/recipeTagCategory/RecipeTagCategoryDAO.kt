package week.on.a.plate.data.repository.tables.filters.recipeTagCategory


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeTagCategoryDAO {
    @Query("SELECT * FROM RecipeTagCategoryRoom")
    suspend fun getAll(): List<RecipeTagCategoryRoom>

    @Transaction
    @Query("SELECT * FROM RecipeTagCategoryRoom WHERE recipeTagCategoryId=:recipeTagCategoryId")
    suspend fun getRecipeTagCategoryAndRecipeTag(recipeTagCategoryId:Long): RecipeTagCategoryAndRecipeTag

    @Transaction
    @Query("SELECT * FROM RecipeTagCategoryRoom ")
    fun getAllCategoryAndTagsFlow(): Flow<List<RecipeTagCategoryAndRecipeTag>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeTagCategoryRoom: RecipeTagCategoryRoom):Long

    @Update
    suspend fun update(recipeTagCategoryRoom: RecipeTagCategoryRoom)

    @Query("DELETE FROM RecipeTagCategoryRoom WHERE recipeTagCategoryId = :id")
    suspend fun deleteById(id: Long)

    @Transaction
    @Query("SELECT * FROM RecipeTagCategoryRoom WHERE recipeTagCategoryId = :id")
    suspend fun getById(id: Long):RecipeTagCategoryAndRecipeTag?

    @Query("SELECT * FROM RecipeTagCategoryRoom WHERE recipeTagCategoryId=:id")
    suspend fun findCategoryById(id: Long) :RecipeTagCategoryRoom?
}
