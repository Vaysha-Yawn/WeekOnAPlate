package week.on.a.plate.data.repository.hilt

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import week.on.a.plate.data.repository.tables.RecipeDB
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemDAO
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleRecipeDB {

    @Provides
    @Singleton
    fun provideDB(app: Application) = RecipeDB.buildDatabase(app.applicationContext)

    @Provides
    @Singleton
    fun provideIngredientDAO(recipeDB: RecipeDB) = recipeDB.daoIngredient()

    @Provides
    @Singleton
    fun provideIngredientCategoryDAO(recipeDB: RecipeDB) = recipeDB.daoIngredientCategory()

    @Provides
    @Singleton
    fun provideIngredientInRecipeDAO(recipeDB: RecipeDB) = recipeDB.daoIngredientInRecipe()

    @Provides
    @Singleton
    fun provideRecipeDAO(recipeDB: RecipeDB) = recipeDB.daoRecipe()

    @Provides
    @Singleton
    fun provideRecipeRecipeTagCrossRefDAO(recipeDB: RecipeDB) = recipeDB.daoRecipeRecipeTagCrossRef()

    @Provides
    @Singleton
    fun provideRecipeStepDAO(recipeDB: RecipeDB) = recipeDB.daoRecipeStep()

    @Provides
    @Singleton
    fun provideRecipeTagDAO(recipeDB: RecipeDB) =recipeDB.daoRecipeTag()

    @Provides
    @Singleton
    fun provideRecipeTagCategoryDAO(recipeDB: RecipeDB) = recipeDB.daoRecipeTagCategory()

    @Provides
    @Singleton
    fun provideRecipeInMenuDAO(recipeDB: RecipeDB) = recipeDB.daoRecipeInMenu()

    @Provides
    @Singleton
    fun provideSelectionInDayDAO(recipeDB: RecipeDB) = recipeDB.daoSelectionInDay()

    @Provides
    @Singleton
    fun providePositionIngredientDAO(recipeDB: RecipeDB) = recipeDB.daoPositionIngredient()

    @Provides
    @Singleton
    fun providePositionNoteDAO(recipeDB: RecipeDB) = recipeDB.daoPositionNote()

    @Provides
    @Singleton
    fun providePositionDraftDAO(recipeDB: RecipeDB) = recipeDB.daoPositionDraft()

    @Provides
    @Singleton
    fun provideDraftAndIngredientCrossRefDAO(recipeDB: RecipeDB) = recipeDB.daoDraftAndIngredientCrossRef()

    @Provides
    @Singleton
    fun provideDraftAndTagCrossRefDAO(recipeDB: RecipeDB) = recipeDB.daoDraftAndTagCrossRef()

    @Provides
    @Singleton
    fun provideShoppingItemDAO(recipeDB: RecipeDB) = recipeDB.daoShoppingItem()

    @Provides
    @Singleton
    fun provideCookPlannerStep(recipeDB: RecipeDB) = recipeDB.daoCookPlannerStep()

    @Provides
    @Singleton
    fun providePlannerGroup(recipeDB: RecipeDB) = recipeDB.daoCookPlannerGroup()

    @Provides
    @Singleton
    fun provideCategorySelection(recipeDB: RecipeDB) = recipeDB.daoCategorySelection()
}