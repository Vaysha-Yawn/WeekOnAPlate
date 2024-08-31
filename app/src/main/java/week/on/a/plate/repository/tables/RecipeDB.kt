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
import week.on.a.plate.repository.tables.weekOrg.day.DayRoom
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DateTypeConverter
import week.on.a.plate.repository.tables.weekOrg.day.DayInWeekDataTypeConverter
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRoom
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuDAO
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeStateTypeConverter
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionRoom
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionDAO
import week.on.a.plate.repository.tables.weekOrg.week.WeekRoom
import week.on.a.plate.repository.tables.weekOrg.week.WeekDAO


@Database(
    entities = [Ingredient::class, IngredientCategory::class, IngredientInRecipe::class, Recipe::class, RecipeRecipeTagCrossRef::class, RecipeStep::class,
        RecipeTag::class, RecipeTagCategory::class, DayRoom::class, RecipeInMenuRoom::class, SelectionRoom::class, WeekRoom::class
    ], version = 1, exportSchema = false
)
@TypeConverters(
    RecipeStateTypeConverter::class, DayInWeekDataTypeConverter::class,
    DateTypeConverter::class
)
abstract class RecipeDB : RoomDatabase() {
    abstract fun daoIngredient(): IngredientDAO
    abstract fun daoIngredientCategory(): IngredientCategoryDAO
    abstract fun daoIngredientInRecipe(): IngredientInRecipeDAO
    abstract fun daoRecipe(): RecipeDAO
    abstract fun daoRecipeRecipeTagCrossRef(): RecipeRecipeTagCrossRefDAO
    abstract fun daoRecipeStep(): RecipeStepDAO
    abstract fun daoRecipeTag(): RecipeTagDAO
    abstract fun daoRecipeTagCategory(): RecipeTagCategoryDAO
    abstract fun daoDayData(): DayDAO
    abstract fun daoRecipeInMenu(): RecipeInMenuDAO
    abstract fun daoSelectionInDay(): SelectionDAO
    abstract fun daoWeekDataDAO(): WeekDAO

    companion object {
        fun buildDatabase(context: Context): RecipeDB {
            return Room.databaseBuilder(context, RecipeDB::class.java, "database-recipe")
                .build()
        }
    }
}