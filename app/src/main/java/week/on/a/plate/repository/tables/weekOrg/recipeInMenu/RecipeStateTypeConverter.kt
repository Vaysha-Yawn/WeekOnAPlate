package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import androidx.room.TypeConverter
import week.on.a.plate.core.data.recipe.RecipeStateView

class RecipeStateTypeConverter {
    @TypeConverter
    fun toState(value: String) = enumValueOf<RecipeStateView>(value)
    @TypeConverter
    fun fromState(value: RecipeStateView) = value.name
}