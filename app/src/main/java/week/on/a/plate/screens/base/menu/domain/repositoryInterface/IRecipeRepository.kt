package week.on.a.plate.screens.base.menu.domain.repositoryInterface

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView

interface IRecipeRepository {

    suspend fun insert(
        recipeView: Position.PositionRecipeView,
        selectionId: Long
    ): Long

    suspend fun update(
        id: Long,
        recipe: RecipeShortView,
        count: Int,
        selectionId: Long
    )

    suspend fun delete(
        id: Long
    )
}