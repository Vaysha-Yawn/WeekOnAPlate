package week.on.a.plate.data.repository.tables.cookPlanner

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CookPlannerGroupRoom(
    val recipeId: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
