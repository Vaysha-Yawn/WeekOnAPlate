package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SelectionRoom(
    val dayId:Long,
    val category:String,
){
    @PrimaryKey(autoGenerate = true)
    var selectionId: Long = 0
}
