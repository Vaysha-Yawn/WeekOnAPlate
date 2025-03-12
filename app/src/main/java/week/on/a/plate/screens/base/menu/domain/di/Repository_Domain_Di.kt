package week.on.a.plate.screens.base.menu.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import week.on.a.plate.data.repository.room.menu.position.positionRecipe.PositionRecipeRepository
import week.on.a.plate.data.repository.room.menu.selection.WeekMenuRepository
import week.on.a.plate.screens.base.menu.domain.repositoryInterface.IRecipeRepository
import week.on.a.plate.screens.base.menu.domain.repositoryInterface.ISelectionRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class SelectionRepositoryModule {
    @Binds
    abstract fun bindSelectionRepository(
        repo: WeekMenuRepository
    ): ISelectionRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class PositionRecipeRepositoryModule {
    @Binds
    abstract fun bindPositionRecipeRepository(
        repo: PositionRecipeRepository
    ): IRecipeRepository
}