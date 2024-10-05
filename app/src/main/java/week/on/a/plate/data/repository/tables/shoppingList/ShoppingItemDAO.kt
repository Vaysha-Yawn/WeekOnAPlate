package week.on.a.plate.data.repository.tables.shoppingList


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom


@Dao
interface ShoppingItemDAO {
    @Query("SELECT * FROM ShoppingItemRoom WHERE checked=1")
    fun getCheckedFlow(): Flow<List<ShoppingItemRoom>>

    @Query("SELECT * FROM ShoppingItemRoom WHERE checked=0")
    fun getUnCheckedFlow(): Flow<List<ShoppingItemRoom>>

    @Query("SELECT * FROM ShoppingItemRoom")
    suspend fun getAll(): List<ShoppingItemRoom>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingItemRoom: ShoppingItemRoom):Long

    @Update
    suspend fun update(shoppingItemRoom: ShoppingItemRoom)

    @Query("DELETE FROM ShoppingItemRoom WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM ShoppingItemRoom WHERE checked = 1")
    suspend fun deleteAllChecked()

    @Query("DELETE FROM ShoppingItemRoom WHERE ingredientInRecipeId = :id")
    suspend fun deleteAllByIngredientInRecipeId(id: Long)
}
