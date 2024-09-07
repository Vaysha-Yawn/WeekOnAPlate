package week.on.a.plate.repository.tables.recipe.ingredientCategory


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface IngredientCategoryDAO {
    @Query("SELECT * FROM IngredientCategoryRoom")
    fun getAll(): Flow<List<IngredientCategoryRoom>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientCategoryRoom: IngredientCategoryRoom)

    @Transaction
    @Query("SELECT * FROM IngredientCategoryRoom WHERE ingredientCategoryId=:ingredientCategoryId")
    fun getIngredientCategoryAndIngredients(ingredientCategoryId:Long): Flow<IngredientCategoryAndIngredients>

    @Update
    suspend fun update(ingredientCategoryRoom: IngredientCategoryRoom)

    @Delete
    suspend fun delete(ingredientCategoryRoom: IngredientCategoryRoom)
}
