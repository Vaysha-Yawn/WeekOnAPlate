package week.on.a.plate.data.repository.tables.filters.recipeTag


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

    @Query("SELECT * FROM RecipeTagRoom WHERE recipeTagId=:id")
    suspend fun findByID(id:Long): RecipeTagRoom?

    @Query("SELECT * FROM RecipeTagRoom WHERE recipeTagId=:id")
     fun findByIDFlow(id:Long): Flow<RecipeTagRoom?>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeTagRoom: RecipeTagRoom):Long

    @Update
    suspend fun update(recipeTagRoom: RecipeTagRoom)

    @Query("DELETE FROM RecipeTagRoom WHERE recipeTagId = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM RecipeTagRoom WHERE recipeTagCategoryId = :id")
    suspend fun getAllByCategoryId(id: Long):List<RecipeTagRoom>
}
