package week.on.a.plate.repository.tables.weekOrg.position.positionNote


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface PositionNoteDAO {
    @Query("SELECT * FROM PositionNoteRoom")
    suspend fun getAll(): List<PositionNoteRoom>

    @Query("SELECT * FROM PositionNoteRoom WHERE selectionId=:selectionId")
    fun getAllInSel(selectionId:Long): Flow<List<PositionNoteRoom>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positionNoteRoom: PositionNoteRoom):Long

    @Update
    suspend fun update(positionNoteRoom: PositionNoteRoom)

    @Query("DELETE FROM PositionNoteRoom WHERE id = :id")
    suspend fun deleteById(id: Long)
}
