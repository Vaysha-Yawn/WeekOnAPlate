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
    @Query("SELECT * FROM ingredientcategory")
    fun getAll(): Flow<List<IngredientCategory>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientCategory: IngredientCategory)

    @Transaction
    @Query("SELECT * FROM ingredientcategory WHERE ingredientCategoryId=:ingredientCategoryId")
    fun getIngredientCategoryAndIngredients(ingredientCategoryId:Long): Flow<List<IngredientCategoryAndIngredients>>

    @Update
    suspend fun update(ingredientCategory: IngredientCategory)

    @Delete
    suspend fun delete(ingredientCategory: IngredientCategory)
}
