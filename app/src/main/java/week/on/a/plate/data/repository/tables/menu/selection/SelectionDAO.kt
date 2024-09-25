package week.on.a.plate.data.repository.tables.menu.selection


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update


@Dao
interface SelectionDAO {
    @Query("SELECT * FROM selectionroom")
    suspend fun getAll(): List<SelectionRoom>

    @Query("SELECT * FROM selectionroom WHERE selectionId=:selectionId")
    suspend fun findSelection(selectionId:Long): SelectionRoom

    @Transaction
    @Query("SELECT * FROM selectionroom WHERE selectionId=:selectionId")
    suspend fun getSelectionAndRecipesInMenu(selectionId:Long): SelectionAndRecipesInMenu

    @Transaction
    @Query("SELECT * FROM selectionroom WHERE selectionId=:selectionId")
    suspend fun getSelectionAndPositionIngredient(selectionId:Long): SelectionAndPositionIngredient

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectionRoom: SelectionRoom):Long

    @Update
    suspend fun update(selectionRoom: SelectionRoom)

    @Delete
    suspend fun delete(selectionRoom: SelectionRoom)
}
