package week.on.a.plate.repository.tables.weekOrg.recipeInMenu


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeInMenuDAO {
    @Query("SELECT * FROM recipeinmenu")
    fun getAll(): Flow<List<RecipeInMenu>>

    @Transaction
    @Query("SELECT * FROM recipe WHERE recipeId=:recipeId")
    fun getRecipeInMenuAndRecipe(recipeId:Long): Flow<RecipeInMenuAndRecipe>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeInMenu: RecipeInMenu)

    @Update
    suspend fun update(recipeInMenu: RecipeInMenu)

    @Delete
    suspend fun delete(recipeInMenu: RecipeInMenu)
}
