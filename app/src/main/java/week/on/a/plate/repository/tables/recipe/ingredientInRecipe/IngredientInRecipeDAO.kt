package week.on.a.plate.repository.tables.recipe.ingredientInRecipe


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientRoom


@Dao
interface IngredientInRecipeDAO {
    @Query("SELECT * FROM IngredientInRecipeRoom")
    suspend fun getAll(): List<IngredientInRecipeRoom>

    @Query("SELECT * FROM ingredientroom")
    suspend fun getAllIngredients(): List<IngredientRoom>

    @Transaction
    @Query("SELECT * FROM IngredientInRecipeRoom WHERE id=:id")
    suspend fun getIngredientAndIngredientInRecipe(id:Long): IngredientAndIngredientInRecipe

    @Query("SELECT * FROM IngredientRoom WHERE ingredientId = :ingredientId")
    suspend fun getCurrent(ingredientId: Long): IngredientRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientInRecipe: IngredientInRecipeRoom):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientRoom: IngredientRoom):Long

    @Update
    suspend fun update(recipe: IngredientInRecipeRoom)

    @Delete
    suspend fun delete(recipe: IngredientInRecipeRoom)
}
