package week.on.a.plate.data.repository.tables.recipe.recipeStep


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeStepDAO {
    @Query("SELECT * FROM RecipeStepRoom")
    fun getAll(): Flow<List<RecipeStepRoom>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeStepRoom: RecipeStepRoom):Long

    @Update
    suspend fun update(recipeStepRoom: RecipeStepRoom)

    @Query("DELETE FROM RecipeStepRoom WHERE id = :id")
    suspend fun deleteById(id: Long)
}
