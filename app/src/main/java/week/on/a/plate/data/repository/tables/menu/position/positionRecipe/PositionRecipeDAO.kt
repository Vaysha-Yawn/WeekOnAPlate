package week.on.a.plate.data.repository.tables.menu.position.positionRecipe


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRoom


@Dao
interface PositionRecipeDAO {
    @Query("SELECT * FROM positionreciperoom")
    suspend fun getAll(): List<PositionRecipeRoom>

    @Transaction
    @Query("SELECT * FROM RecipeRoom WHERE recipeId=:recipeId")
    suspend fun getRecipeInMenuAndRecipe(recipeId:Long): RecipeInMenuAndRecipe

    @Query("SELECT * FROM positionreciperoom WHERE selectionId=:selectionId")
    suspend fun getAllInSel(selectionId:Long): List<PositionRecipeRoom>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeRoom: RecipeRoom):Long

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positionRecipeRoom: PositionRecipeRoom):Long

    @Update
    suspend fun update(positionRecipeRoom: PositionRecipeRoom)

    @Query("DELETE FROM positionreciperoom WHERE recipeInMenuId = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM positionreciperoom WHERE recipeId = :id")
    suspend fun deleteByRecipeId(id: Long)
}
