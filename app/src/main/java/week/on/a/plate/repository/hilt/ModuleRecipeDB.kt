package week.on.a.plate.repository.hilt

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import week.on.a.plate.repository.tables.RecipeDB
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientRepository
import week.on.a.plate.repository.tables.recipe.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRepository
import week.on.a.plate.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef.RecipeRecipeTagCrossRefRepository
import week.on.a.plate.repository.tables.recipe.recipeStep.RecipeStepRepository
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagRepository
import week.on.a.plate.repository.tables.recipe.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.repository.tables.weekOrg.day.DayDataRepository
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRepository
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDayRepository
import week.on.a.plate.repository.tables.weekOrg.week.WeekDataRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleRecipeDB {

    @Provides
    @Singleton
    fun provideDB(app: Application) = RecipeDB.buildDatabase(app.applicationContext)

    @Provides
    @Singleton
    fun provideIngredientRepository(recipeDB: RecipeDB) = IngredientRepository(recipeDB.daoIngredient())

    @Provides
    @Singleton
    fun provideIngredientCategoryRepository(recipeDB: RecipeDB) = IngredientCategoryRepository(recipeDB.daoIngredientCategory())

    @Provides
    @Singleton
    fun provideIngredientInRecipeRepository(recipeDB: RecipeDB) = IngredientInRecipeRepository(recipeDB.daoIngredientInRecipe())

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDB: RecipeDB) = RecipeRepository(recipeDB.daoRecipe())

    @Provides
    @Singleton
    fun provideRecipeRecipeTagCrossRefRepository(recipeDB: RecipeDB) = RecipeRecipeTagCrossRefRepository(recipeDB.daoRecipeRecipeTagCrossRef())

    @Provides
    @Singleton
    fun provideRecipeStepRepository(recipeDB: RecipeDB) = RecipeStepRepository(recipeDB.daoRecipeStep())

    @Provides
    @Singleton
    fun provideRecipeTagRepository(recipeDB: RecipeDB) = RecipeTagRepository(recipeDB.daoRecipeTag())

    @Provides
    @Singleton
    fun provideRecipeTagCategoryRepository(recipeDB: RecipeDB) = RecipeTagCategoryRepository(recipeDB.daoRecipeTagCategory())

    @Provides
    @Singleton
    fun provideDAODayData(recipeDB: RecipeDB) = DayDataRepository(recipeDB.daoDayData())

    @Provides
    @Singleton
    fun provideRecipeInMenuRepository(recipeDB: RecipeDB) = RecipeInMenuRepository(recipeDB.daoRecipeInMenu())

    @Provides
    @Singleton
    fun provideSelectionInDayRepository(recipeDB: RecipeDB) = SelectionInDayRepository(recipeDB.daoSelectionInDay())

    @Provides
    @Singleton
    fun provideWeekDataRepository(recipeDB: RecipeDB) = WeekDataRepository(recipeDB.daoWeekDataDAO())

}