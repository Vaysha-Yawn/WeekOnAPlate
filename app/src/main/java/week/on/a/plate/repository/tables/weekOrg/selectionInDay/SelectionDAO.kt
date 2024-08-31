package week.on.a.plate.repository.tables.weekOrg.selectionInDay


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRoom


@Dao
interface SelectionDAO {
    @Query("SELECT * FROM selectionroom")
    fun getAll(): Flow<List<SelectionRoom>>

    @Query("SELECT * FROM selectionroom WHERE selectionId=:selectionId")
    fun findSelection(selectionId:Long): Flow<SelectionRoom>

    @Transaction
    @Query("SELECT * FROM selectionroom WHERE selectionId=:selectionId")
    fun getSelectionAndRecipesInMenu(selectionId:Long): Flow<SelectionAndRecipesInMenu>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeInMenu: RecipeInMenuRoom)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectionRoom: SelectionRoom)

    @Update
    suspend fun update(selectionRoom: SelectionRoom)

    @Delete
    suspend fun delete(selectionRoom: SelectionRoom)
}
