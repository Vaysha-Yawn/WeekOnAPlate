package week.on.a.plate.data.repository.tables.recipe.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.utils.LocalDateTimeTypeConverter
import week.on.a.plate.data.repository.utils.LocalTimeTypeConverter
import java.time.LocalDateTime
import java.time.LocalTime

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
    //add
    @TypeConverters(LocalTimeTypeConverter::class)
    val duration: LocalTime,
) {
    @PrimaryKey(autoGenerate = true)
    var recipeId: Long = 0
}
