package week.on.a.plate.data.repository.tables.menu.position.positionRecipe


import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import javax.inject.Inject


class PositionRecipeRepository @Inject constructor(
    private val positionRecipeDAO: PositionRecipeDAO
) {
    private val mapper = RecipeInMenuMapper()

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        return positionRecipeDAO.getAllInSel(selectionId).map { recipeInMenu ->
            with(mapper) {
                recipeInMenu.roomToView(
                    recipeInMenu.recipeId, recipeInMenu.recipeName, recipeInMenu.recipeImg
                )
            }
        }
    }

    suspend fun insert(recipeView: Position.PositionRecipeView, selectionId: Long): Long {
        val positionRoom = with(mapper) { recipeView.viewToRoom(selectionId) }
        return positionRecipeDAO.insert(positionRoom)
    }

    suspend fun update(id: Long, recipe: RecipeShortView, count: Int, selectionId: Long) {
        positionRecipeDAO.update(
            PositionRecipeRoom(recipe.id, recipe.name, count, selectionId, recipe.image).apply {
                this.recipeInMenuId = id
            }
        )
    }

    suspend fun delete(id: Long) {
        positionRecipeDAO.deleteById(id)
    }

    suspend fun deleteByRecipeId(id: Long) {
        positionRecipeDAO.deleteByRecipeId(id)
    }


}
