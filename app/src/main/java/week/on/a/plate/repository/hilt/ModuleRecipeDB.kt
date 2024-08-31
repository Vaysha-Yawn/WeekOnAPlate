package week.on.a.plate.repository.hilt

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import week.on.a.plate.repository.tables.RecipeDB
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
    fun provideDayDAO(recipeDB: RecipeDB) = recipeDB.daoDayData()

    @Provides
    @Singleton
    fun provideRecipeInMenuDAO(recipeDB: RecipeDB) = recipeDB.daoRecipeInMenu()

    @Provides
    @Singleton
    fun provideSelectionInDayDAO(recipeDB: RecipeDB) = recipeDB.daoSelectionInDay()

    @Provides
    @Singleton
    fun provideWeekDAO(recipeDB: RecipeDB) = recipeDB.daoWeekDataDAO()

}