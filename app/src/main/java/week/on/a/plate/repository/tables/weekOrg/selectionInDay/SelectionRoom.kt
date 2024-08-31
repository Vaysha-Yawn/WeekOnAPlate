package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SelectionRoom(
    // ссылка на день, может быть 0 если это нераспред недели
    val dayId:Long,
    val category:String,
){
    @PrimaryKey(autoGenerate = true)
    var selectionId: Long = 0
}
