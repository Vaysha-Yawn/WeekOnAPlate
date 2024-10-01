package week.on.a.plate.data.repository.tables.recipe.recipeRecipeTagCrossRef

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeTagCrossRefRepository @Inject constructor(
    private val daoTags: RecipeRecipeTagCrossRefDAO,
) {

    private val tagMapper = RecipeTagMapper()

    suspend fun getTags(recipeId: Long): List<RecipeTagView> {
        return daoTags.getRecipeAndRecipeTagByRecipeId(recipeId).flatMap { it.recipeTagRooms }
            .map { with(tagMapper) { it.roomToView() } }
    }

    fun getTagsFlow(recipeId: Long): Flow<List<RecipeTagView>> {
        return daoTags.getRecipeAndRecipeTagByRecipeIdFlow(recipeId).map {recipeAndRecipeTags->
            recipeAndRecipeTags.flatMap { it.recipeTagRooms }.map { with(tagMapper) { it.roomToView() } }
        }
    }

    suspend fun insertTagRef(list: List<RecipeTagView>, recipeId: Long) {
        val tagsCrossRef = list.map { RecipeRecipeTagCrossRef(recipeId, it.id) }
        tagsCrossRef.forEach { daoTags.insert(it) }
    }

    suspend fun deleteTagRef(recipe: RecipeTagView, recipeId: Long) {
         daoTags.deleteByIdTag(recipeId, recipe.id)
    }

    suspend fun deleteByRecipeId(recipeId: Long) {
        daoTags.deleteByRecipeId(recipeId)
    }
}