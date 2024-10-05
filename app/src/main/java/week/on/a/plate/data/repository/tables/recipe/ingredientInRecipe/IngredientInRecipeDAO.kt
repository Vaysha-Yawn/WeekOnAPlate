package week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRoom
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeAndIngredientInRecipe


@Dao
interface IngredientInRecipeDAO {
    @Query("SELECT * FROM IngredientInRecipeRoom")
    suspend fun getAll(): List<IngredientInRecipeRoom>

    @Transaction
    @Query("SELECT * FROM reciperoom WHERE recipeId=:recipeId")
    suspend fun getRecipeAndIngredientInRecipe(recipeId: Long): RecipeAndIngredientInRecipe

    @Transaction
    @Query("SELECT * FROM reciperoom WHERE recipeId=:recipeId")
     fun getRecipeAndIngredientInRecipeFlow(recipeId: Long): Flow<RecipeAndIngredientInRecipe>

    @Transaction
    @Query("SELECT * FROM IngredientInRecipeRoom WHERE id=:id")
    suspend fun getIngredientAndIngredientInRecipe(id: Long): IngredientAndIngredientInRecipe

    @Query("SELECT * FROM IngredientRoom WHERE ingredientId = :ingredientId")
    suspend fun getCurrent(ingredientId: Long): IngredientRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientInRecipe: IngredientInRecipeRoom): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientRoom: IngredientRoom): Long

    @Update
    suspend fun update(recipe: IngredientInRecipeRoom)

    @Query("DELETE FROM IngredientInRecipeRoom WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM IngredientInRecipeRoom WHERE recipeId = :recipeIdd")
    suspend fun deleteByRecipeId(recipeIdd: Long)

    @Query("DELETE FROM IngredientInRecipeRoom WHERE ingredientId = :id")
    suspend fun deleteByIngredientId(id: Long)

    @Query("SELECT * FROM IngredientInRecipeRoom WHERE ingredientId = :id")
    suspend fun getAllByIngredientId(id: Long): List<IngredientInRecipeRoom>
}
