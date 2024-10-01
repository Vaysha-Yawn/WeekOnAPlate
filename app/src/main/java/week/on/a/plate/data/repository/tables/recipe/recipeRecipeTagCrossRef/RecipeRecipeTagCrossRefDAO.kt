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

    @Transaction
    @Query("SELECT * FROM RecipeRoom WHERE recipeId=:recipeIdd")
    suspend fun getRecipeAndRecipeTagByRecipeId(recipeIdd: Long): List<RecipeAndRecipeTag>

    @Transaction
    @Query("SELECT * FROM RecipeRoom WHERE recipeId=:recipeIdd")
    fun getRecipeAndRecipeTagByRecipeIdFlow(recipeIdd: Long): Flow<List<RecipeAndRecipeTag>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeRecipeTagCrossRef: RecipeRecipeTagCrossRef):Long

    @Update
    suspend fun update(recipeRecipeTagCrossRef: RecipeRecipeTagCrossRef)

    @Query("DELETE FROM reciperecipetagcrossref WHERE recipeId = :recipeIdd")
    suspend fun deleteByRecipeId(recipeIdd: Long)

    @Query("DELETE FROM reciperecipetagcrossref WHERE recipeId = :recipeIdd AND recipeTagId=:tagID")
    suspend fun deleteByIdTag(recipeIdd: Long, tagID:Long)
}
