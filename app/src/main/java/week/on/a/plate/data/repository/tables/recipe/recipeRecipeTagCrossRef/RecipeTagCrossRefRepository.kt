package week.on.a.plate.data.repository.tables.recipe.recipeRecipeTagCrossRef

import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagMapper
import javax.inject.Singleton

@Singleton
class RecipeTagCrossRefRepository(
    private val daoTags: RecipeRecipeTagCrossRefDAO,
) {

    private val tagMapper = RecipeTagMapper()

    suspend fun getTags(recipeId: Long): List<RecipeTagView> {
        return daoTags.getRecipeAndRecipeTagByRecipeId(recipeId).flatMap { it.recipeTagRooms }
            .map { with(tagMapper) { it.roomToView() } }
    }

    suspend fun insertTagRef(list: List<RecipeTagView>, recipeId: Long) {
        val tagsCrossRef = list.map { RecipeRecipeTagCrossRef(recipeId, it.id) }
        tagsCrossRef.forEach { daoTags.insert(it) }
    }

    suspend fun deleteTagRef(list: List<RecipeTagView>, recipeId: Long) {
        list.forEach { daoTags.deleteByIdTag(recipeId, it.id) }
    }

    suspend fun deleteByRecipeId(recipeId: Long) {
        daoTags.deleteByRecipeId(recipeId)
    }
}