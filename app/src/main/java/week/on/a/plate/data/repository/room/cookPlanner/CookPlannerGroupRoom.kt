package week.on.a.plate.data.repository.room.cookPlanner

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.utils.LocalDateTimeTypeConverter
import java.time.LocalDateTime


@Entity
data class CookPlannerGroupRoom(
    val recipeId: Long,
    var portionsCount: Int,
    @TypeConverters(LocalDateTimeTypeConverter::class)
    var start: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}