package week.on.a.plate.repository.tables.recipe.ingredient


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface IngredientDAO {
    @Query("SELECT * FROM ingredient")
    fun getAll(): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredient WHERE ingredientId = :ingredientId")
    fun getCurrent(ingredientId: Long): Flow<Ingredient>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: Ingredient)

    @Update
    suspend fun update(ingredient: Ingredient)

    @Query("UPDATE ingredient SET img=:img WHERE ingredientId = :ingredientId")
    suspend fun update(ingredientId: Long, img: String, )

    @Delete
    suspend fun delete(ingredient: Ingredient)
}
