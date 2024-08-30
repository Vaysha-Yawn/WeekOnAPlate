package week.on.a.plate.repository.tables.recipe.recipeStep


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeStepDAO {
    @Query("SELECT * FROM recipestep")
    fun getAll(): Flow<List<RecipeStep>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeStep: RecipeStep)

    @Update
    suspend fun update(recipeStep: RecipeStep)

    @Delete
    suspend fun delete(recipeStep: RecipeStep)
}
