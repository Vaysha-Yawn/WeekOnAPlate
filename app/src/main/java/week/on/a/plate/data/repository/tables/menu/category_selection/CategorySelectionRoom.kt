package week.on.a.plate.data.repository.tables.menu.category_selection

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.tables.menu.selection.LocalDateTimeTypeConverter
import week.on.a.plate.data.repository.tables.menu.selection.LocalTimeTypeConverter
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
data class CategorySelectionRoom(
    val name:String,
    @TypeConverters(LocalTimeTypeConverter::class)
    val stdTime: LocalTime,
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
