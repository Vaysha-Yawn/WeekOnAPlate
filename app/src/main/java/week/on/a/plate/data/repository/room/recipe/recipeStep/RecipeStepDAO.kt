package week.on.a.plate.data.repository.room.recipe.recipeStep


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeAndRecipeSteps


@Dao
interface RecipeStepDAO {

    @Transaction
    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    suspend fun getRecipeAndRecipeSteps(recipeId: Long): RecipeAndRecipeSteps

    @Query("SELECT * FROM recipesteproom WHERE id=:id")
    suspend fun getStepById(id: Long): RecipeStepRoom?

    @Transaction
    @Query("SELECT * FROM recipeRoom WHERE recipeId=:recipeId")
    fun getRecipeAndRecipeStepsFlow(recipeId: Long): Flow<RecipeAndRecipeSteps>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(step: RecipeStepRoom): Long

    @Update
    suspend fun update(step: RecipeStepRoom)

    @Query("DELETE FROM recipesteproom WHERE recipeId = :recipeIdd")
    suspend fun deleteByRecipeId(recipeIdd: Long)

    @Query("DELETE FROM recipesteproom WHERE id = :stepId")
    suspend fun deleteByIdStep(stepId: Long)
}
