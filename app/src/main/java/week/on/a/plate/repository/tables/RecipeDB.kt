package week.on.a.plate.repository.tables


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import week.on.a.plate.repository.tables.recipe.ingredient.Ingredient
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientDAO
import week.on.a.plate.repository.tables.recipe.ingredientCategory.IngredientCategory
import week.on.a.plate.repository.tables.recipe.ingredientCategory.IngredientCategoryDAO
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipe
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.repository.tables.recipe.recipe.Recipe
import week.on.a.plate.repository.tables.recipe.recipe.RecipeDAO
import week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef.RecipeRecipeTagCrossRef
import week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef.RecipeRecipeTagCrossRefDAO
import week.on.a.plate.repository.tables.recipe.recipeStep.RecipeStep
import week.on.a.plate.repository.tables.recipe.recipeStep.RecipeStepDAO
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTag
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagDAO
import week.on.a.plate.repository.tables.recipe.recipeTagCategory.RecipeTagCategory
import week.on.a.plate.repository.tables.recipe.recipeTagCategory.RecipeTagCategoryDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayData
import week.on.a.plate.repository.tables.weekOrg.day.DayDataDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayInWeekDataTypeConverter
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenu
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuDAO
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeStateTypeConverter
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDay
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDayDAO
import week.on.a.plate.repository.tables.weekOrg.week.WeekData
import week.on.a.plate.repository.tables.weekOrg.week.WeekDataDAO


@Database(
    entities = [Ingredient::class, IngredientCategory::class, IngredientInRecipe::class, Recipe::class, RecipeRecipeTagCrossRef::class, RecipeStep::class,
        RecipeTag::class, RecipeTagCategory::class, DayData::class, RecipeInMenu::class, SelectionInDay::class, WeekData::class
    ], version = 1, exportSchema = false
)
@TypeConverters(RecipeStateTypeConverter::class, DayInWeekDataTypeConverter::class)
abstract class RecipeDB : RoomDatabase() {
    abstract fun daoIngredient(): IngredientDAO
    abstract fun daoIngredientCategory(): IngredientCategoryDAO
    abstract fun daoIngredientInRecipe(): IngredientInRecipeDAO
    abstract fun daoRecipe(): RecipeDAO
    abstract fun daoRecipeRecipeTagCrossRef(): RecipeRecipeTagCrossRefDAO
    abstract fun daoRecipeStep(): RecipeStepDAO
    abstract fun daoRecipeTag(): RecipeTagDAO
    abstract fun daoRecipeTagCategory(): RecipeTagCategoryDAO
    abstract fun daoDayData(): DayDataDAO
    abstract fun daoRecipeInMenu(): RecipeInMenuDAO
    abstract fun daoSelectionInDay(): SelectionInDayDAO
    abstract fun daoWeekDataDAO(): WeekDataDAO

    companion object {
        fun buildDatabase(context: Context): RecipeDB {
            return Room.databaseBuilder(context, RecipeDB::class.java, "database-recipe")
                .build()
        }
    }
}