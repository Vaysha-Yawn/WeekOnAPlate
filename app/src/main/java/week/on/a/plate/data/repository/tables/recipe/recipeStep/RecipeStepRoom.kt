package week.on.a.plate.data.repository.tables.recipe.recipeStep

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.tables.menu.selection.ListIntConverter
import week.on.a.plate.data.repository.tables.menu.selection.LocalDateTimeTypeConverter
import week.on.a.plate.data.repository.tables.menu.selection.LocalTimeTypeConverter
import java.time.LocalTime

@Entity
data class RecipeStepRoom(
    val recipeId: Long = 0,
    val description: String,
    val image:String,
    val timer:Long,
    val start:Long,
    val duration:Long,
    @TypeConverters(ListIntConverter::class)
    val ingredientsPinned:List<Long>
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
