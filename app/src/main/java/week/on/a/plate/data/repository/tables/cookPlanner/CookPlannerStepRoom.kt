package week.on.a.plate.data.repository.tables.cookPlanner

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.tables.menu.selection.LocalDateTimeTypeConverter
import week.on.a.plate.dialogs.changePortions.event.ChangePortionsCountEvent
import java.time.LocalDateTime

@Entity
data class CookPlannerStepRoom(
    val plannerGroupId: Long,
    val originalStepId: Long,
    var checked: Boolean,
    //remove
    /*@TypeConverters(LocalDateTimeTypeConverter::class)
    var start: LocalDateTime,
    @TypeConverters(LocalDateTimeTypeConverter::class)
    var end: LocalDateTime,*/
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
