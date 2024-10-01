package week.on.a.plate.data.repository.tables.menu.position.positionRecipe


import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import javax.inject.Inject


class PositionRecipeRepository @Inject constructor(
    private val recipeInMenuDAO: RecipeInMenuDAO
) {
    private val mapper = RecipeInMenuMapper()

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        return recipeInMenuDAO.getAllInSel(selectionId).map { recipeInMenu ->
            with(mapper) {
                recipeInMenu.roomToView(
                    recipeInMenu.recipeId, recipeInMenu.recipeName
                )
            }
        }
    }

    suspend fun insert(recipeView: Position.PositionRecipeView, selectionId: Long): Long {
        val positionRoom = with(mapper) { recipeView.viewToRoom(selectionId) }
        return recipeInMenuDAO.insert(positionRoom)
    }

    suspend fun update(id: Long, recipe: RecipeShortView, count: Int, selectionId: Long) {
        recipeInMenuDAO.update(
            PositionRecipeRoom(recipe.id, recipe.name, count, selectionId).apply {
                this.recipeInMenuId = id
            }
        )
    }

    suspend fun delete(id: Long) {
        recipeInMenuDAO.deleteById(id)
    }


}
