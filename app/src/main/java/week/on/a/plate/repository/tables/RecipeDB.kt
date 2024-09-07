package week.on.a.plate.repository.tables


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientDAO
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientRoom
import week.on.a.plate.repository.tables.recipe.ingredientCategory.IngredientCategoryDAO
import week.on.a.plate.repository.tables.recipe.ingredientCategory.IngredientCategoryRoom
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom
import week.on.a.plate.repository.tables.recipe.recipe.RecipeDAO
import week.on.a.plate.repository.tables.recipe.recipe.RecipeRoom
import week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef.RecipeRecipeTagCrossRef
import week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef.RecipeRecipeTagCrossRefDAO
import week.on.a.plate.repository.tables.recipe.recipeStep.RecipeStepDAO
import week.on.a.plate.repository.tables.recipe.recipeStep.RecipeStepRoom
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagDAO
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagRoom
import week.on.a.plate.repository.tables.recipe.recipeTagCategory.RecipeTagCategoryDAO
import week.on.a.plate.repository.tables.recipe.recipeTagCategory.RecipeTagCategoryRoom
import week.on.a.plate.repository.tables.weekOrg.day.DateTypeConverter
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayInWeekDataTypeConverter
import week.on.a.plate.repository.tables.weekOrg.day.DayRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef.DraftAndIngredientCrossRef
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef.DraftAndIngredientCrossRefDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftTagCrossRef.DraftAndTagCrossRef
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftTagCrossRef.DraftAndTagCrossRefDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteRoom
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRoom
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.RecipeInMenuDAO
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionDAO
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionRoom
import week.on.a.plate.repository.tables.weekOrg.week.WeekDAO
import week.on.a.plate.repository.tables.weekOrg.week.WeekRoom


@Database(
    entities = [IngredientRoom::class, IngredientCategoryRoom::class, IngredientInRecipeRoom::class, RecipeRoom::class, RecipeRecipeTagCrossRef::class, RecipeStepRoom::class,
        RecipeTagRoom::class, RecipeTagCategoryRoom::class, DayRoom::class, PositionRecipeRoom::class, SelectionRoom::class, WeekRoom::class,
        PositionIngredientRoom::class, PositionNoteRoom::class, PositionDraftRoom::class, DraftAndIngredientCrossRef::class, DraftAndTagCrossRef::class
    ], version = 1, exportSchema = false
)
@TypeConverters(
    DayInWeekDataTypeConverter::class,
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
    abstract fun daoPositionIngredient(): PositionIngredientDAO
    abstract fun daoPositionNote(): PositionNoteDAO
    abstract fun daoPositionDraft(): PositionDraftDAO
    abstract fun daoDraftAndIngredientCrossRef(): DraftAndIngredientCrossRefDAO
    abstract fun daoDraftAndTagCrossRef(): DraftAndTagCrossRefDAO


    companion object {
        fun buildDatabase(context: Context): RecipeDB {
            return Room.databaseBuilder(context, RecipeDB::class.java, "database-recipe")
                .build()
        }
    }
}