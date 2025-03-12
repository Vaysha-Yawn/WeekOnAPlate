package week.on.a.plate.data.repository.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerGroupDAO
import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerGroupRoom
import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerStepDAO
import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerStepRoom
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientDAO
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientRoom
import week.on.a.plate.data.repository.room.filters.ingredientCategory.IngredientCategoryDAO
import week.on.a.plate.data.repository.room.filters.ingredientCategory.IngredientCategoryRoom
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagDAO
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRoom
import week.on.a.plate.data.repository.room.filters.recipeTagCategory.RecipeTagCategoryDAO
import week.on.a.plate.data.repository.room.filters.recipeTagCategory.RecipeTagCategoryRoom
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionRoom
import week.on.a.plate.data.repository.room.menu.position.draft.PositionDraftDAO
import week.on.a.plate.data.repository.room.menu.position.draft.PositionDraftRoom
import week.on.a.plate.data.repository.room.menu.position.draft.draftIngredientCrossRef.DraftAndIngredientCrossRef
import week.on.a.plate.data.repository.room.menu.position.draft.draftIngredientCrossRef.DraftAndIngredientCrossRefDAO
import week.on.a.plate.data.repository.room.menu.position.draft.draftTagCrossRef.DraftAndTagCrossRef
import week.on.a.plate.data.repository.room.menu.position.draft.draftTagCrossRef.DraftAndTagCrossRefDAO
import week.on.a.plate.data.repository.room.menu.position.note.PositionNoteDAO
import week.on.a.plate.data.repository.room.menu.position.note.PositionNoteRoom
import week.on.a.plate.data.repository.room.menu.position.positionIngredient.PositionIngredientDAO
import week.on.a.plate.data.repository.room.menu.position.positionIngredient.PositionIngredientRoom
import week.on.a.plate.data.repository.room.menu.position.positionRecipe.PositionRecipeDAO
import week.on.a.plate.data.repository.room.menu.position.positionRecipe.PositionRecipeRoom
import week.on.a.plate.data.repository.room.menu.selection.SelectionDAO
import week.on.a.plate.data.repository.room.menu.selection.SelectionRoom
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeRoom
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeDAO
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRoom
import week.on.a.plate.data.repository.room.recipe.recipeRecipeTagCrossRef.RecipeRecipeTagCrossRef
import week.on.a.plate.data.repository.room.recipe.recipeRecipeTagCrossRef.RecipeRecipeTagCrossRefDAO
import week.on.a.plate.data.repository.room.recipe.recipeStep.RecipeStepDAO
import week.on.a.plate.data.repository.room.recipe.recipeStep.RecipeStepRoom
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemDAO
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRoom
import week.on.a.plate.data.repository.utils.DateTypeConverter
import week.on.a.plate.data.repository.utils.ListIntConverter
import week.on.a.plate.data.repository.utils.LocalDateTimeTypeConverter
import week.on.a.plate.data.repository.utils.LocalTimeTypeConverter


@Database(
    entities = [IngredientRoom::class, IngredientCategoryRoom::class, IngredientInRecipeRoom::class, RecipeRoom::class, RecipeRecipeTagCrossRef::class, RecipeStepRoom::class,
        RecipeTagRoom::class, RecipeTagCategoryRoom::class, PositionRecipeRoom::class, SelectionRoom::class,
        PositionIngredientRoom::class, PositionNoteRoom::class, PositionDraftRoom::class, DraftAndIngredientCrossRef::class, DraftAndTagCrossRef::class,
        ShoppingItemRoom::class, CookPlannerStepRoom::class, CookPlannerGroupRoom::class, CategorySelectionRoom::class
    ],
    version = 1,
    /*autoMigrations = [
       AutoMigration (from = 1, to = 2)
    ],*/
    exportSchema = true,
)

@TypeConverters(
    DateTypeConverter::class,
    LocalDateTimeTypeConverter::class,
    LocalTimeTypeConverter::class,
    ListIntConverter::class
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
    abstract fun daoRecipeInMenu(): PositionRecipeDAO
    abstract fun daoSelectionInDay(): SelectionDAO
    abstract fun daoPositionIngredient(): PositionIngredientDAO
    abstract fun daoPositionNote(): PositionNoteDAO
    abstract fun daoPositionDraft(): PositionDraftDAO
    abstract fun daoDraftAndIngredientCrossRef(): DraftAndIngredientCrossRefDAO
    abstract fun daoDraftAndTagCrossRef(): DraftAndTagCrossRefDAO
    abstract fun daoShoppingItem(): ShoppingItemDAO
    abstract fun daoCookPlannerStep(): CookPlannerStepDAO
    abstract fun daoCookPlannerGroup(): CookPlannerGroupDAO
    abstract fun daoCategorySelection(): CategorySelectionDAO

    companion object {
        fun buildDatabase(context: Context): RecipeDB {
            return Room.databaseBuilder(context, RecipeDB::class.java, "database-recipe")
                .build()
        }
    }
}