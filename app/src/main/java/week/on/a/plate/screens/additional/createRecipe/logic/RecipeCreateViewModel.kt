package week.on.a.plate.screens.additional.createRecipe.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.logic.ExitApplyViewModel
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.CreateRecipeUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.EditRecipeUseCaseDB
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.EditTagsUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateImageUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateIngredientUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateStepUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateTimeUseCase
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.state.RecipeStepState
import week.on.a.plate.screens.additional.filters.state.FilterResult
import week.on.a.plate.screens.base.menu.domain.dbusecase.GetRecipeUseCase
import javax.inject.Inject


@HiltViewModel
class RecipeCreateViewModel @Inject constructor(
    private val editTagsUseCase: EditTagsUseCase,
    private val recipeCreateImageUseCase: RecipeCreateImageUseCase,
    private val recipeCreateTimeUseCase: RecipeCreateTimeUseCase,
    private val recipeCreateStepUseCase: RecipeCreateStepUseCase,
    private val recipeCreateIngredientUseCase: RecipeCreateIngredientUseCase,
    private val getRecipe: GetRecipeUseCase,
    private val editRecipe: EditRecipeUseCaseDB,
    private val createRecipe: CreateRecipeUseCase,
) : ViewModel() {

    var state = RecipeCreateUIState()
    var oldRecipe: RecipeView? = null
    val dialogOpenParams: MutableStateFlow<DialogOpenParams?> = MutableStateFlow(null)
    val mainEvent: MutableState<MainEvent?> = mutableStateOf(null)

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainEvent.value = event
            is RecipeCreateEvent -> onEvent(
                event
            )
        }
    }

    fun onEvent(event: RecipeCreateEvent) {
        viewModelScope.launch {
            when (event) {
                RecipeCreateEvent.Close -> mainEvent.value =
                    MainEvent.NavigateBack

                RecipeCreateEvent.Done -> done()
                RecipeCreateEvent.EditTags -> editTagsUseCase(
                    state.tags
                ) { mainEvent.value = it }

                RecipeCreateEvent.AddIngredient -> recipeCreateIngredientUseCase.addIngredient(
                    dialogOpenParams, state
                )

                is RecipeCreateEvent.EditIngredient -> recipeCreateIngredientUseCase.editIngredient(
                    event, dialogOpenParams, state
                )

                is RecipeCreateEvent.DeleteStep -> recipeCreateStepUseCase.deleteStep(
                    event, state
                )

                is RecipeCreateEvent.ClearTimer -> recipeCreateTimeUseCase.clearTimer(
                    event
                )

                is RecipeCreateEvent.DeleteImage -> recipeCreateImageUseCase.deleteImage(
                    event
                )

                is RecipeCreateEvent.EditTimer -> recipeCreateTimeUseCase.editTimer(
                    dialogOpenParams,
                    event
                )

                RecipeCreateEvent.AddStep -> recipeCreateStepUseCase.addStep(state)
                is RecipeCreateEvent.EditImage -> recipeCreateImageUseCase.editImage(
                    dialogOpenParams, viewModelScope, event
                )

                is RecipeCreateEvent.EditMainImage -> recipeCreateImageUseCase.editMainImage(
                    state, dialogOpenParams, viewModelScope, event
                )

                RecipeCreateEvent.AddManyIngredients -> recipeCreateIngredientUseCase.addManyIngredients(
                    state
                ) { mainEvent.value = it }

                is RecipeCreateEvent.DeleteIngredient -> recipeCreateIngredientUseCase.deleteIngredient(
                    event.ingredient, state
                )

                is RecipeCreateEvent.EditPinnedIngredients -> recipeCreateStepUseCase.editPinnedIngredients(
                    event.recipeStepState, dialogOpenParams, state
                )

                is RecipeCreateEvent.EditRecipeDuration -> recipeCreateTimeUseCase.editRecipeDuration(
                    dialogOpenParams,
                    state
                )

                RecipeCreateEvent.OpenDialogExitApplyFromCreateRecipe -> openDialogExitApplyFromCreateRecipe()
            }
        }
    }

    fun applyToStateAddManyIngredients(
        filterRes: FilterResult,
    ) {
        val ingredientsOld = state.ingredients.value.map { it.ingredientView }
        val currentIngredients = state.ingredients.value
        val ingredientsNew = filterRes.ingredients ?: return

        val listToAdd = ingredientsNew.toMutableList().apply {
            removeAll(ingredientsOld)
        }.toList()

        val listToDelete = ingredientsOld.toMutableList().apply {
            removeAll(ingredientsNew)
        }.toList()

        val currentIngredientsMutableCopy = currentIngredients.toMutableList()

        listToAdd.forEach { ingredient ->
            currentIngredientsMutableCopy.add(IngredientInRecipeView(0, ingredient, "", 0))
        }

        listToDelete.forEach { ingredient ->
            val t =
                currentIngredients.find { it.ingredientView.ingredientId == ingredient.ingredientId }
            currentIngredientsMutableCopy.remove(t)
        }

        state.ingredients.value = currentIngredientsMutableCopy.toList()
    }

    private fun openDialogExitApplyFromCreateRecipe() {
        val params = ExitApplyViewModel.ExitApplyDialogParams { event ->
            if (event == ExitApplyEvent.Exit) {
                mainEvent.value = MainEvent.NavigateBack
            }
        }
        dialogOpenParams.value = params
    }

    private fun done() {
        viewModelScope.launch {
            //todo значок загрузки пока сохраняется как состояние экрана
            if (state.isForCreate.value) {
                createRecipe(state)
            } else {
                editRecipe(state, oldRecipe!!)
            }
            oldRecipe = null
            mainEvent.value = MainEvent.NavigateBack
        }
    }

    fun launch(
        oldRecipeId: Long?, isForCreate: Boolean, startRecipe: RecipeView?
    ) {
        if (oldRecipeId != null) setStateByOldRecipe(oldRecipeId) else if (startRecipe != null)
            setStateRecipe(startRecipe) else state = RecipeCreateUIState()
        state.isForCreate.value = isForCreate
    }

    private fun setStateByOldRecipe(oldRecipeId: Long) {
        viewModelScope.launch {
            //todo значок загрузки пока инициализируется состояние экрана
            val oldRecipeAsync = viewModelScope.async() {
                getRecipe(oldRecipeId)
            }
            oldRecipe = oldRecipeAsync.await()
            setStateRecipe(oldRecipe)
        }
    }

    private fun setStateRecipe(recipe: RecipeView?) {
        if (recipe == null) {
            state = RecipeCreateUIState()
        } else {
            viewModelScope.launch {
                state.link.value = recipe.link
                state.photoLink.value = recipe.img
                state.name.value = recipe.name
                state.description.value = recipe.description
                state.portionsCount.intValue = recipe.standardPortionsCount
                state.tags.value = recipe.tags
                state.ingredients.value = recipe.ingredients
                state.mainImageContainer.value = null

                val list = mutableListOf<RecipeStepState>()
                recipe.steps.forEach { stepOld ->
                    val step =
                        RecipeStepState(stepOld.id).also { stepState ->
                            with(stepState) {
                                description.value = stepOld.description
                                image.value = stepOld.image
                                timer.longValue = stepOld.timer
                                pinnedIngredientsInd.value = stepOld.ingredientsPinnedId
                                imageContainer.value = null
                            }
                        }
                    list.add(step)
                }
                state.steps.value = list
            }
        }
    }
}