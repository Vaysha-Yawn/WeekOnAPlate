package week.on.a.plate.screens.additional.createRecipe.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.BackNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.event.NavigateBackDest
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.logic.ExitApplyViewModel
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.EditTagsUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateImageUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateIngredientUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateStepUseCase
import week.on.a.plate.screens.additional.createRecipe.logic.useCase.RecipeCreateTimeUseCase
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.state.RecipeStepState
import javax.inject.Inject


@HiltViewModel
class RecipeCreateViewModel @Inject constructor(
    private val editTagsUseCase: EditTagsUseCase,
    private val recipeCreateImageUseCase: RecipeCreateImageUseCase,
    private val recipeCreateTimeUseCase: RecipeCreateTimeUseCase,
    private val recipeCreateStepUseCase: RecipeCreateStepUseCase,
    private val recipeCreateIngredientUseCase: RecipeCreateIngredientUseCase,
) : ViewModel() {

    var state = RecipeCreateUIState()
    private lateinit var resultFlow: MutableStateFlow<RecipeCreateUIState?>
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
                    MainEvent.Navigate(NavigateBackDest, BackNavParams)
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

    private fun openDialogExitApplyFromCreateRecipe() {
        val params = ExitApplyViewModel.ExitApplyDialogParams { event ->
            if (event == ExitApplyEvent.Exit) {
                mainEvent.value = MainEvent.Navigate(NavigateBackDest, BackNavParams)
            }
        }
        dialogOpenParams.value = params
    }

    private fun done() {
        resultFlow.value = state
        mainEvent.value = MainEvent.Navigate(NavigateBackDest, BackNavParams)
    }

    fun start(): Flow<RecipeCreateUIState?> {
        val flow = MutableStateFlow<RecipeCreateUIState?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(
        oldRecipe: RecipeView?, isForCreate: Boolean,
        use: (RecipeCreateUIState) -> Unit
    ) {
        if (oldRecipe != null) setStateByOldRecipe(oldRecipe) else state = RecipeCreateUIState()

        state.isForCreate.value = isForCreate

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

    private fun setStateByOldRecipe(oldRecipe: RecipeView) {
        state.source.value = oldRecipe.link
        state.photoLink.value = oldRecipe.img
        state.name.value = oldRecipe.name
        state.description.value = oldRecipe.description
        state.portionsCount.intValue = oldRecipe.standardPortionsCount
        state.tags.value = oldRecipe.tags
        state.ingredients.value = oldRecipe.ingredients
        state.mainImageContainer.value = null

        val list = mutableListOf<RecipeStepState>()
        oldRecipe.steps.forEach { stepOld ->
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