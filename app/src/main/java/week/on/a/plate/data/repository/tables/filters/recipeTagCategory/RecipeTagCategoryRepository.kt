package week.on.a.plate.data.repository.tables.filters.recipeTagCategory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.R
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagDAO
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagMapper
import javax.inject.Inject

val startCategoryName = R.string.no_category

class RecipeTagCategoryRepository @Inject constructor(
    private val dao: RecipeTagCategoryDAO,
    private val daoTag: RecipeTagDAO,
) {

    private val recipeTagMapper = RecipeTagMapper()
    private val categoryMapper = RecipeTagCategoryMapper()

    fun getAllTagsByCategoriesForFilters(): Flow<List<TagCategoryView>> {
        return dao.getAllCategoryAndTagsFlow().map { recipeTagCategoryAndRecipeTags ->
            recipeTagCategoryAndRecipeTags.map { categoryAndTagRoom ->
                val listTags = categoryAndTagRoom.tags.map { tagRoom ->
                    with(recipeTagMapper) {
                        tagRoom.roomToView()
                    }
                }
                with(categoryMapper) {
                    categoryAndTagRoom.tagCategory.roomToView(listTags)
                }
            }
        }
    }

    suspend fun create(name: String): Long {
        val category = RecipeTagCategoryRoom(name)
        return dao.insert(category)
    }

    suspend fun updateName(newName: String, id: Long) {
        dao.update(RecipeTagCategoryRoom(newName).apply { this.recipeTagCategoryId = id })
    }

    suspend fun getById(id: Long): TagCategoryView? {
        val t = dao.getById(id) ?: return null
        val list = t.tags.map {
            with(recipeTagMapper) {
                it.roomToView()
            }
        }
        return with(categoryMapper) {
            t.tagCategory.roomToView(list)
        }
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
        val tags = daoTag.getAllByCategoryId(id)
        tags.forEach {
            daoTag.update(it.apply { recipeTagCategoryId = 1 })
        }
    }

    suspend fun isStartCategoryInstalled():Boolean {
        return dao.findCategoryById(1)!=null
    }
}




