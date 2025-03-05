package week.on.a.plate.data.repository.tables.recipe.recipe

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.zip
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerGroupDAO
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepDAO
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRoom
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRepository
import week.on.a.plate.data.repository.tables.recipe.recipeRecipeTagCrossRef.RecipeTagCrossRefRepository
import week.on.a.plate.data.repository.tables.recipe.recipeStep.StepRepository
import week.on.a.plate.data.repository.utils.updateListOfEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val dao: RecipeDAO,
    private val stepRepository: StepRepository,
    private val tagCrossRefRepository: RecipeTagCrossRefRepository,
    private val ingredientInRecipeRepository: IngredientInRecipeRepository,
    private val positionRecipeRepository: PositionRecipeRepository,

    private val groupRepo: CookPlannerGroupDAO,
    private val cookPlannerStepDAO: CookPlannerStepDAO,
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

    fun getAllRecipeFlow(): Flow<List<RecipeView>> {
        return dao.getAllFlow().map { roomRecipeList ->
            roomRecipeList.map { recipeRoom ->
                getRecipe(recipeRoom.recipeId)
            }
        }
    }

    fun getRecipeFlow(id: Long): Flow<RecipeView?> {
        val recipeF = dao.getRecipeByIdFlow(id).onEmpty { emit(null) }
        val stepsF = stepRepository.getStepsFlow(id).onEmpty { emit(emptyList()) }
        val ingredientsF =
            ingredientInRecipeRepository.getIngredientsFlow(id).onEmpty { emit(emptyList()) }
        val tagsF = tagCrossRefRepository.getTagsFlow(id).onEmpty { emit(emptyList()) }
        return combine(recipeF, stepsF, ingredientsF, tagsF) { recipe, steps, ingredients, tags ->
            with(recipeMapper) {
                recipe?.roomToView(
                    tags = tags,
                    ingredients = ingredients,
                    steps = steps
                )
            }
        }
    }

    private fun <A, B, C, D, E> zip(
        flowA: Flow<A>,
        flowB: Flow<B>,
        flowC: Flow<C>,
        flowD: Flow<D>,
        transform: (A, B, C, D) -> E
    ): Flow<E> {
        return flowA.zip(flowB) { a, b ->
            Pair(a, b)
        }.zip(flowC) { pairab, c ->
            Pair(pairab, c)
        }.zip(flowD) { pairabc, d ->
            transform(pairabc.first.first, pairabc.first.second, pairabc.second, d)
        }
    }


    suspend fun create(recipeView: RecipeView): Long {
        val recipe = with(recipeMapper) {
            recipeView.viewToRoom()
        }
        val recipeId = dao.insert(recipe)

        recipeView.steps.forEach {
            stepRepository.insertStep(it, recipeId)
        }

        recipeView.tags.forEach {
            tagCrossRefRepository.insertTagRef(it, recipeId)
        }

        recipeView.ingredients.forEach {
            ingredientInRecipeRepository.insertIngredient(
                it,
                recipeId
            )
        }

        return recipeId
    }

    suspend fun updateRecipe(oldRecipe: RecipeView, updatedRecipe: RecipeView) {
        val recipeId = oldRecipe.id

        val updatedRoomRecipe =
            with(recipeMapper) { updatedRecipe.viewToRoom() }.apply { this.recipeId = recipeId }
        dao.update(updatedRoomRecipe)

        updateListOfEntity(
            oldList = oldRecipe.ingredients,
            newList = updatedRecipe.ingredients,
            findSameInList = { ingr, list ->
                list.find { ingrFind -> ingrFind.id == ingr.id }
            },
            insertAction = { insertIngredient(it, recipeId) },
            deleteAction = {
                deleteIngredient(it)
            },
            updateAction = { updateIngredient(it, recipeId) }
        )


        updateListOfEntity(
            oldList = oldRecipe.steps,
            newList = updatedRecipe.steps,
            findSameInList = { stepView, list ->
                list.find { stepFind -> stepFind.id == stepView.id }
            },
            insertAction = { step ->
                val newId = stepRepository.insertStep(step, recipeId)
                addStepForUpdateRecipe(step, recipeId, newId)
            },
            deleteAction = { step ->
                stepRepository.deleteStep(step)
                deleteStepForUpdateRecipe(step)
            },
            updateAction = { step ->
                stepRepository.update(step, recipeId)
                updStepForUpdateRecipe(step)
            }
        )

        updateListOfEntity(
            oldList = oldRecipe.tags,
            newList = updatedRecipe.tags,
            findSameInList = { tagView, list ->
                list.find { tag -> tag.id == tagView.id }
            },
            insertAction = { tags -> tagCrossRefRepository.insertTagRef(tags, recipeId) },
            deleteAction = { tag -> tagCrossRefRepository.deleteTagRef(tag, recipeId) },
            updateAction = { tag -> tagCrossRefRepository.update(tag, recipeId) },
        )
    }

    /// cook planner methods
    private suspend fun deleteGroupByRecipeId(id: Long) {
        val allGroups = groupRepo.getAllByRecipeId(id)
        if (allGroups.isEmpty()) return
        allGroups.forEach {
            val groupId = it.id
            groupRepo.deleteById(groupId)
            cookPlannerStepDAO.deleteByIdGroup(groupId)
        }
    }

    private suspend fun updStepForUpdateRecipe(step: RecipeStepView) {
        val steps = cookPlannerStepDAO.getAllByOriginalStepId(step.id)
        if (steps.isEmpty()) return
        steps.forEach { stepCook->
            cookPlannerStepDAO.update(stepCook)
        }
    }

    private suspend fun addStepForUpdateRecipe(step: RecipeStepView, recipeId: Long, newStepId: Long) {
        val groups = groupRepo.getAllByRecipeId(recipeId)
        if (groups.isEmpty()) return
        groups.forEach { group->
            val stepRoom = CookPlannerStepRoom(
                group.id,
                newStepId,
                false
            )
            cookPlannerStepDAO.insert(stepRoom)
        }
    }

    private suspend fun deleteStepForUpdateRecipe(step: RecipeStepView) {
        val stepsForDel = cookPlannerStepDAO.getAllByOriginalStepId(step.id)
        if (stepsForDel.isEmpty()) return
        stepsForDel.forEach { stepDel->
            cookPlannerStepDAO.deleteById(stepDel.id)
        }
    }
    //

    private suspend fun updateIngredient(new: IngredientInRecipeView, recipeId: Long) {
        ingredientInRecipeRepository.update(
            new, recipeId
        )
    }

    private suspend fun deleteIngredient(ingredient: IngredientInRecipeView) {
        ingredientInRecipeRepository.deleteIngredients(
            ingredient
        )
    }

    private suspend fun insertIngredient(list: IngredientInRecipeView, recipeId: Long) {
        ingredientInRecipeRepository.insertIngredient(
            list,
            recipeId
        )
    }

    suspend fun updateRecipeFavorite(oldRecipe: RecipeView, inFavoriteNow: Boolean) {
        val recipeId = oldRecipe.id
        val updatedRoomRecipe = with(recipeMapper) { oldRecipe.viewToRoom() }.apply {
            this.recipeId = recipeId
            this.inFavorite = inFavoriteNow
        }
        dao.update(updatedRoomRecipe)
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
        stepRepository.deleteByRecipeId(id)
        tagCrossRefRepository.deleteByRecipeId(id)
        ingredientInRecipeRepository.deleteByRecipeId(id)
        positionRecipeRepository.deleteByRecipeId(id)
        deleteGroupByRecipeId(id)
    }
}