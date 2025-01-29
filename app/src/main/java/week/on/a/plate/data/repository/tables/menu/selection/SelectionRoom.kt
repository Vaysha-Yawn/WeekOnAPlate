package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.utils.LocalDateTimeTypeConverter
import java.time.LocalDateTime

@Entity
data class SelectionRoom(
    val name:String,
    @TypeConverters(LocalDateTimeTypeConverter::class)
    val dateTime: LocalDateTime,
    val weekOfYear:Int,
    val isForWeek:Boolean,
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
