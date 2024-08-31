package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuMapper
import javax.inject.Singleton

@Singleton
class SelectionRepository(val dao: SelectionDAO) {

    fun read(): Flow<List<SelectionRoom>> = dao.getAll()

    fun findSelection(selectionId: Long): Flow<SelectionRoom> = dao.findSelection(selectionId)

    fun getSelectionAndRecipesInMenu(selectionId:Long): Flow<SelectionAndRecipesInMenu> =
        dao.getSelectionAndRecipesInMenu(selectionId)

    suspend fun insert(dayId:Long, selection: SelectionView) {
        val selRoom = with(SelectionMapper()) {
            selection.viewToRoom(dayId)
        }
        dao.insert(selRoom)

        //
        selection.recipes.forEach { recipeInMenu ->
            val recipeInMenuRoom = with(RecipeInMenuMapper()) {
                recipeInMenu.viewToRoom(selection.id)
            }
            dao.insert(recipeInMenuRoom)
        }
    }

    suspend fun update(selectionRoom: SelectionRoom) = dao.update(selectionRoom)

    suspend fun delete(selectionRoom: SelectionRoom) = dao.delete(selectionRoom)

}