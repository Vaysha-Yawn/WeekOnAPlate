package week.on.a.plate.data.repository.tables.recipe.recipe

import week.on.a.plate.core.utils.ListComparator
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRepository
import week.on.a.plate.data.repository.tables.recipe.recipeRecipeTagCrossRef.RecipeTagCrossRefRepository
import week.on.a.plate.data.repository.tables.recipe.recipeStep.StepRepository
import javax.inject.Singleton

@Singleton
class RecipeRepository(
    private val dao: RecipeDAO,
    private val stepRepository: StepRepository,
    private val tagCrossRefRepository: RecipeTagCrossRefRepository,
    private val ingredientInRecipeRepository: IngredientInRecipeRepository,
) {
    private val recipeMapper = RecipeMapper()

    suspend fun getRecipe(id: Long): RecipeView {
        val recipe = dao.getRecipeById(id)
        val steps = stepRepository.getSteps(id)
        val ingredients = ingredientInRecipeRepository.getIngredients(id)
        val tags = tagCrossRefRepository.getTags(id)

        return with(recipeMapper) {
            recipe.roomToView(
                tags = tags,
                ingredients = ingredients,
                steps = steps
            )
        }
    }

    suspend fun create(recipeView: RecipeView): Long {
        val recipe = with(recipeMapper) {
            recipeView.viewToRoom()
        }
        val recipeId = dao.insert(recipe)

        stepRepository.insertSteps(recipeView.steps, recipeId)

        tagCrossRefRepository.insertTagRef(recipeView.tags, recipeId)

        ingredientInRecipeRepository.insertIngredients(recipeView.ingredients, recipeId)

        return recipeId
    }

    suspend fun updateRecipe(oldRecipe: RecipeView, updatedRecipe: RecipeView) {
        val recipeId = oldRecipe.id

        val updatedRoomRecipe = with(recipeMapper) { updatedRecipe.viewToRoom() }.apply { this.recipeId = recipeId }
        dao.update(updatedRoomRecipe)

        updateListOfEntity(
            oldList = oldRecipe.steps,
            newList = updatedRecipe.steps,
            insertAction = { steps -> stepRepository.insertSteps(steps, recipeId) },
            deleteAction = { steps -> stepRepository.deleteSteps(steps, recipeId) }
        )

        updateListOfEntity(
            oldList = oldRecipe.tags,
            newList = updatedRecipe.tags,
            insertAction = { tags -> tagCrossRefRepository.insertTagRef(tags, recipeId) },
            deleteAction = { tags -> tagCrossRefRepository.deleteTagRef(tags, recipeId) }
        )

        updateListOfEntity(
            oldList = oldRecipe.ingredients,
            newList = updatedRecipe.ingredients,
            insertAction = { ingredients -> ingredientInRecipeRepository.insertIngredients(ingredients, recipeId) },
            deleteAction = { ingredients -> ingredientInRecipeRepository.deleteIngredients(ingredients) }
        )
    }

    private suspend fun <T> updateListOfEntity(
        oldList: List<T>,
        newList: List<T>,
        insertAction: suspend (List<T>) -> Unit,
        deleteAction: suspend (List<T>) -> Unit
    ) {
        val itemsToAdd = ListComparator.addComparator(oldList, newList)
        val itemsToRemove = ListComparator.removedComparator(oldList, newList)

        if (itemsToAdd.isNotEmpty()) insertAction(itemsToAdd)
        if (itemsToRemove.isNotEmpty()) deleteAction(itemsToRemove)
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
        stepRepository.deleteByRecipeId(id)
        tagCrossRefRepository.deleteByRecipeId(id)
        ingredientInRecipeRepository.deleteByRecipeId(id)
    }
}




