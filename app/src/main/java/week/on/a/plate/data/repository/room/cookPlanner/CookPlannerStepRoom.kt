package week.on.a.plate.data.repository.room.cookPlanner

import androidx.room.Entity
import androidx.room.PrimaryKey

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
