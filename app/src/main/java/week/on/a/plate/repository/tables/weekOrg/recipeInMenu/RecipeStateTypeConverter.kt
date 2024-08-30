package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import androidx.room.TypeConverter
import week.on.a.plate.core.data.recipe.RecipeState

class RecipeStateTypeConverter {
    @TypeConverter
    fun toState(value: String) = enumValueOf<RecipeState>(value)
    @TypeConverter
    fun fromState(value: RecipeState) = value.name
}