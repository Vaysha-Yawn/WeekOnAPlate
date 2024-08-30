package week.on.a.plate.repository.tables.recipe.ingredientInRecipe


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface IngredientInRecipeDAO {
    @Query("SELECT * FROM ingredientinrecipe")
    fun getAll(): Flow<List<IngredientInRecipe>>

    @Transaction
    @Query("SELECT * FROM ingredient WHERE ingredientId=:ingredientId")
    fun getIngredientAndIngredientInRecipe(ingredientId:Long): Flow<IngredientAndIngredientInRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: IngredientInRecipe)

    @Update
    suspend fun update(recipe: IngredientInRecipe)

    @Delete
    suspend fun delete(recipe: IngredientInRecipe)
}
