package week.on.a.plate.repository.tables.recipe.ingredient


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagRoom


@Dao
interface IngredientDAO {
    @Query("SELECT * FROM IngredientRoom")
    suspend fun getAll(): List<IngredientRoom>

    @Query("SELECT * FROM IngredientRoom WHERE ingredientId = :ingredientId")
    suspend fun getCurrent(ingredientId: Long): IngredientRoom

    @Query("SELECT * FROM IngredientRoom WHERE name=:namew")
    suspend fun findByName(namew:String): IngredientRoom?

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientRoom: IngredientRoom):Long

    @Update
    suspend fun update(ingredientRoom: IngredientRoom)

    @Query("UPDATE IngredientRoom SET img=:img WHERE ingredientId = :ingredientId")
    suspend fun update(ingredientId: Long, img: String)

    @Query("DELETE FROM IngredientRoom WHERE ingredientId = :id")
    suspend fun deleteById(id: Long)
}
