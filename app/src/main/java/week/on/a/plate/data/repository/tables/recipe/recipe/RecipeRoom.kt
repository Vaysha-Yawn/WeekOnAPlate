package week.on.a.plate.data.repository.tables.recipe.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.tables.menu.selection.DateTypeConverter
import week.on.a.plate.data.repository.tables.menu.selection.LocalTimeTypeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class RecipeRoom(
    val name: String,
    val description: String,
    val img:String,
    val prepTime: Int,
    val allTime: Int,
    val standardPortionsCount: Int,
    val link: String,
    var inFavorite:Boolean,
    @TypeConverters(DateTypeConverter::class)
    val dateCreated: LocalDate,
    @TypeConverters(DateTypeConverter::class)
    val dateLastEdit: LocalDate,
    @TypeConverters(LocalTimeTypeConverter::class)
    val timeLastEdit: LocalTime,
){
    @PrimaryKey(autoGenerate = true)
    var recipeId: Long = 0
}
