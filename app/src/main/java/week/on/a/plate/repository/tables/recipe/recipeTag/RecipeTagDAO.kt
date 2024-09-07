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
    @Query("SELECT * FROM RecipeTagRoom")
    fun getAll(): Flow<List<RecipeTagRoom>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeTagRoom: RecipeTagRoom)

    @Update
    suspend fun update(recipeTagRoom: RecipeTagRoom)

    @Delete
    suspend fun delete(recipeTagRoom: RecipeTagRoom)
}
