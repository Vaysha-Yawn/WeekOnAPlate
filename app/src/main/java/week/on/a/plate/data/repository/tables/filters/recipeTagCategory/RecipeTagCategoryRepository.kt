package week.on.a.plate.data.repository.tables.filters.recipeTagCategory

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagMapper
import javax.inject.Singleton

@Singleton
class RecipeTagCategoryRepository(val dao: RecipeTagCategoryDAO) {

    private val recipeTagMapper = RecipeTagMapper()
    private val categoryMapper = RecipeTagCategoryMapper()

    suspend fun getAllTagsByCategoriesForFilters(): List<TagCategoryView> {
        val listAllRoom = dao.getAllCategoryAndTags()
        val listResult = mutableListOf<TagCategoryView>()
        listAllRoom.forEach { categoryAndTagRoom ->
            val listTags = mutableListOf<RecipeTagView>()
            categoryAndTagRoom.tags.forEach { tagRoom ->
                val tagView = with(recipeTagMapper) {
                    tagRoom.roomToView()
                }
                listTags.add(tagView)
            }
            val categoryView = with(categoryMapper) {
                categoryAndTagRoom.tagCategory.roomToView(listTags)
            }
            listResult.add(categoryView)
        }
        return listResult
    }

    suspend fun create(name:String):Long {
        val category = RecipeTagCategoryRoom(name)
        return dao.insert(category)
    }

    suspend fun updateName(newName: String, id: Long) {
        dao.update(RecipeTagCategoryRoom(newName).apply { this.recipeTagCategoryId=id })
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
}




