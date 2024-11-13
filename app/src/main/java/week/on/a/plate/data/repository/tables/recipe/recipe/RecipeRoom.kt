package week.on.a.plate.data.repository.tables.recipe.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.tables.menu.selection.LocalDateTimeTypeConverter
import java.time.LocalDateTime

@Entity
data class RecipeRoom(
    val name: String,
    val description: String,
    val img: String,
    val standardPortionsCount: Int,
    val link: String,
    var inFavorite: Boolean,
    @TypeConverters(LocalDateTimeTypeConverter::class)
    val lastEdit: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var recipeId: Long = 0
}
