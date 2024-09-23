package week.on.a.plate.repository.tables.recipe.recipeTag


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeTagDAO {
    @Query("SELECT * FROM RecipeTagRoom")
    fun getAll(): Flow<List<RecipeTagRoom>>

    @Query("SELECT * FROM RecipeTagRoom WHERE tagName=:name")
    suspend fun findByName(name:String): RecipeTagRoom?

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeTagRoom: RecipeTagRoom):Long

    @Update
    suspend fun update(recipeTagRoom: RecipeTagRoom)

    @Delete
    suspend fun delete(recipeTagRoom: RecipeTagRoom)
}
