package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class SelectionRoom(
    val name:String,
    @TypeConverters(DateTypeConverter::class)
    val date: LocalDate,
    val weekOfYear:Int,
    val isForWeek:Boolean,
    @TypeConverters(LocalTimeTypeConverter::class)
    val time: LocalTime,
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
