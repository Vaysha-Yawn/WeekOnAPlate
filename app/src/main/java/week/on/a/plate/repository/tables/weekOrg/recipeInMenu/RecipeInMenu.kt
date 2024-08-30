package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.core.data.recipe.RecipeState

@Entity
data class RecipeInMenu(
    @PrimaryKey(autoGenerate = true)
    val recipeInMenuId: Long = 0,
    @TypeConverters(RecipeStateTypeConverter::class)
    val state: RecipeState,
    val recipeId: Long,
    var portionsCount: Int,
    val selectionId: Long,
)
