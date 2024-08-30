package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class SelectionInDayRepository(val dao: SelectionInDayDAO) {

    fun read(): Flow<List<SelectionInDay>> = dao.getAll()

    fun findSelection(selectionId: Long): Flow<SelectionInDay> = dao.findSelection(selectionId)

    fun getSelectionAndRecipesInMenu(selectionId: Long): Flow<SelectionAndRecipesInMenu> =
        dao.getSelectionAndRecipesInMenu(selectionId)

    suspend fun create(selectionInDay: SelectionInDay) = dao.insert(selectionInDay)

    suspend fun update(selectionInDay: SelectionInDay) = dao.update(selectionInDay)

    suspend fun delete(selectionInDay: SelectionInDay) = dao.delete(selectionInDay)

}