package week.on.a.plate.repository.tables.recipe.recipeTag


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeTagDAO {
    @Query("SELECT * FROM recipeTag")
    fun getAll(): Flow<List<RecipeTag>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeTag: RecipeTag)

    @Update
    suspend fun update(recipeTag: RecipeTag)

    @Delete
    suspend fun delete(recipeTag: RecipeTag)
}
