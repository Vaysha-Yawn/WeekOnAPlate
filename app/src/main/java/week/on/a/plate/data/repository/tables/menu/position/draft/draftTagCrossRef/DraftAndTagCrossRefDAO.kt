package week.on.a.plate.data.repository.tables.menu.position.draft.draftTagCrossRef


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface DraftAndTagCrossRefDAO {
    @Query("SELECT * FROM draftandtagcrossref")
    fun getAll(): Flow<List<DraftAndTagCrossRef>>

    @Transaction
    @Query("SELECT * FROM PositionDraftRoom")
    fun getDraftAndTag(): Flow<List<DraftAndTag>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(draftAndTagCrossRef: DraftAndTagCrossRef)

    @Update
    suspend fun update(draftAndTagCrossRef: DraftAndTagCrossRef)

    @Delete
    suspend fun delete(draftAndTagCrossRef: DraftAndTagCrossRef)
}
