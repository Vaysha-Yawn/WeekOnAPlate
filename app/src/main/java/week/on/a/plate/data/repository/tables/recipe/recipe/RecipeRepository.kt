package week.on.a.plate.data.repository.tables.recipe.recipe

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import week.on.a.plate.data.dataView.recipe.RecipeView
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

     private fun <A, B, C, D, E> zip(flowA:Flow<A>, flowB:Flow<B>, flowC:Flow<C>, flowD:Flow<D>, transform:(A, B, C, D)->E):Flow<E> {
         return flowA.zip(flowB){a, b->
             Pair(a,b)
         }.zip(flowC){pairab, c->
             Pair(pairab, c)
         }.zip(flowD){pairabc, d->
             transform(pairabc.first.first, pairabc.first.second, pairabc.second, d)
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

        val updatedRoomRecipe =
            with(recipeMapper) { updatedRecipe.viewToRoom() }.apply { this.recipeId = recipeId }
        dao.update(updatedRoomRecipe)

        updateListOfEntity(
            oldList = oldRecipe.steps,
            newList = updatedRecipe.steps,
            insertAction = { steps -> stepRepository.insertSteps(steps, recipeId) },
            deleteAction = { step -> stepRepository.deleteStep(step) }
        )

        updateListOfEntity(
            oldList = oldRecipe.tags,
            newList = updatedRecipe.tags,
            insertAction = { tags -> tagCrossRefRepository.insertTagRef(tags, recipeId) },
            deleteAction = { tag -> tagCrossRefRepository.deleteTagRef(tag, recipeId) }
        )

        updateListOfEntity(
            oldList = oldRecipe.ingredients,
            newList = updatedRecipe.ingredients,
            insertAction = { ingredients ->
                ingredientInRecipeRepository.insertIngredients(
                    ingredients,
                    recipeId
                )
            },
            deleteAction = { ingredients ->
                ingredientInRecipeRepository.deleteIngredients(
                    ingredients
                )
            }
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
    }
}




