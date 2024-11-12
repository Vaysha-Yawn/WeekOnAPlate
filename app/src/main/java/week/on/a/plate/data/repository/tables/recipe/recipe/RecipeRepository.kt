package week.on.a.plate.data.repository.tables.recipe.recipe

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerGroupDAO
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepDAO
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
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
    private val stepRepo: CookPlannerStepDAO,
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

    suspend fun getAllRecipe(): List<RecipeView> {
        return dao.getAll().map { recipeRoom ->
            getRecipe(recipeRoom.recipeId)
        }
    }

    fun getAllRecipeFlow(): Flow<List<RecipeView>> {
        return dao.getAllFlow().map { roomRecipeList ->
            roomRecipeList.map { recipeRoom ->
                getRecipe(recipeRoom.recipeId)
            }
        }
    }

    fun getRecipeFlow(id: Long): Flow<RecipeView> {
        val recipeF = dao.getRecipeByIdFlow(id)
        val stepsF = stepRepository.getStepsFlow(id)
        val ingredientsF = ingredientInRecipeRepository.getIngredientsFlow(id)
        val tagsF = tagCrossRefRepository.getTagsFlow(id)
        return zip(recipeF, stepsF, ingredientsF, tagsF) { recipe, steps, ingredients, tags ->
            with(recipeMapper) {
                recipe.roomToView(
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
                stepRepository.insertStep(step, recipeId)
                addStepForUpdateRecipe(step, recipeId)
            },
            deleteAction = { step ->
                stepRepository.deleteStep(step)
                deleteStepForUpdateRecipe(step)
            },
            updateAction = { step ->
                stepRepository.update(step, recipeId)
                //todo upd time steps in cook planner
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

    ///
    private suspend fun deleteGroupByRecipeId(id: Long) {
        val allGroups = groupRepo.getAllByRecipeId(id)
        allGroups.forEach {
            val groupId = it.id
            groupRepo.deleteById(groupId)
            stepRepo.deleteByIdGroup(groupId)
        }
    }

    private suspend fun addStepForUpdateRecipe(step: RecipeStepView, recipeId: Long) {
        val groups = groupRepo.getAllByRecipeId(recipeId)
        groups.forEach { group->
            val allHave = stepRepo.getByGroupId(group.id)
            val start = allHave.minOf { it.start }
            val stepRoom = CookPlannerStepRoom(
                group.id,
                step.id,
                false,
                start.plusSeconds(step.start),
                start.plusSeconds(step.duration).plusSeconds(step.start),
            )
            stepRepo.insert(stepRoom)
        }
    }

    private suspend fun deleteStepForUpdateRecipe(step: RecipeStepView) {
        val stepsForDel = stepRepo.getAllByOriginalStepId(step.id)
        stepsForDel.forEach { stepDel->
            stepRepo.deleteById(stepDel.id)
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