package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
