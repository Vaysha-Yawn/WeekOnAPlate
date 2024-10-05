package week.on.a.plate.data.repository.tables.filters.ingredientCategory


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
    suspend fun getAll(): List<IngredientCategoryRoom>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredientCategoryRoom: IngredientCategoryRoom):Long

    @Transaction
    @Query("SELECT * FROM IngredientCategoryRoom WHERE ingredientCategoryId=:ingredientCategoryId")
    suspend fun getById(ingredientCategoryId:Long): IngredientCategoryAndIngredients

    @Transaction
    @Query("SELECT * FROM IngredientCategoryRoom WHERE ingredientCategoryId=:ingredientCategoryId")
    suspend fun getIngredientCategoryAndIngredients(ingredientCategoryId:Long): IngredientCategoryAndIngredients

    @Transaction
    @Query("SELECT * FROM IngredientCategoryRoom ")
    fun getAllIngredientCategoryAndIngredients(): Flow<List<IngredientCategoryAndIngredients>>

    @Update
    suspend fun update(ingredientCategoryRoom: IngredientCategoryRoom)

    @Query("DELETE FROM IngredientCategoryRoom WHERE ingredientCategoryId = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM IngredientCategoryRoom WHERE name=:startCategoryName")
    suspend fun findCategoryByName(startCategoryName: String): IngredientCategoryRoom?
}
