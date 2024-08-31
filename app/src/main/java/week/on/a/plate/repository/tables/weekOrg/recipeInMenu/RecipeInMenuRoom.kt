package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.core.data.recipe.RecipeStateView

@Entity
data class RecipeInMenuRoom(
    @TypeConverters(RecipeStateTypeConverter::class)
    val state: RecipeStateView,
    val recipeId: Long,
    val recipeName: String,
    var portionsCount: Int,
    val selectionId: Long,
){
    @PrimaryKey(autoGenerate = true)
    var recipeInMenuId: Long = 0
}
