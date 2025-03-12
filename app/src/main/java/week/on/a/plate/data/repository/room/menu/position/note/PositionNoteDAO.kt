package week.on.a.plate.data.repository.room.menu.position.note


import androidx.room.Dao
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
    suspend fun getAllInSel(selectionId: Long): List<PositionNoteRoom>

    @Query("SELECT * FROM PositionNoteRoom WHERE selectionId=:selectionId")
    fun getAllInSelFlow(selectionId: Long): Flow<List<PositionNoteRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positionNoteRoom: PositionNoteRoom): Long

    @Update
    suspend fun update(positionNoteRoom: PositionNoteRoom)

    @Query("DELETE FROM PositionNoteRoom WHERE id = :id")
    suspend fun deleteById(id: Long)
}
