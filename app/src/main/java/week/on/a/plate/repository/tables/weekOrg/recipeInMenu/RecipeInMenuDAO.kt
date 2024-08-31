package week.on.a.plate.repository.tables.weekOrg.recipeInMenu


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.recipe.recipe.Recipe


@Dao
interface RecipeInMenuDAO {
    @Query("SELECT * FROM recipeinmenuroom")
    suspend fun getAll(): List<RecipeInMenuRoom>

    @Transaction
    @Query("SELECT * FROM recipe WHERE recipeId=:recipeId")
    suspend fun getRecipeInMenuAndRecipe(recipeId:Long): RecipeInMenuAndRecipe

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeRoom: Recipe):Long

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeInMenuRoom: RecipeInMenuRoom):Long

    @Update
    suspend fun update(recipeInMenuRoom: RecipeInMenuRoom)

    @Delete
    suspend fun delete(recipeInMenuRoom: RecipeInMenuRoom)
}
