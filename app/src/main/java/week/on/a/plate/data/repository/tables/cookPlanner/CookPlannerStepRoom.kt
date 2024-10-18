package week.on.a.plate.data.repository.tables.cookPlanner

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.tables.menu.selection.LocalDateTimeTypeConverter
import java.time.LocalDateTime

@Entity
data class CookPlannerStepRoom(
    val recipeId: Long,
    val plannerGroupId: Long,
    val originalStepId: Long,
    val checked: Boolean,
    @TypeConverters(LocalDateTimeTypeConverter::class)
    val start: LocalDateTime,
    @TypeConverters(LocalDateTimeTypeConverter::class)
    val end: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
