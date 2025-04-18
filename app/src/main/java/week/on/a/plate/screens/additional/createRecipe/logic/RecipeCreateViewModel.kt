package week.on.a.plate.screens.additional.createRecipe.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
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
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    var state = RecipeCreateUIState()
    private lateinit var resultFlow: MutableStateFlow<RecipeCreateUIState?>

    private lateinit var recipeCreateStepUseCase: RecipeCreateStepUseCase
    private lateinit var recipeCreateIngredientUseCase: RecipeCreateIngredientUseCase

    val dialogOpenParams: MutableState<DialogOpenParams?> = mutableStateOf(null)

    fun initWithMainVM(mainViewModel: MainViewModel) {
        recipeCreateStepUseCase = RecipeCreateStepUseCase(mainViewModel, state)
        recipeCreateIngredientUseCase = RecipeCreateIngredientUseCase(mainViewModel, state)
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)
            is RecipeCreateEvent -> onEvent(
                event
            )
        }
    }

    fun onEvent(event: RecipeCreateEvent) {
        viewModelScope.launch {
            when (event) {
                RecipeCreateEvent.Close -> mainViewModel.nav.popBackStack()
                RecipeCreateEvent.Done -> done()
                RecipeCreateEvent.EditTags -> editTagsUseCase(
                    mainViewModel,
                    state.tags
                )

                RecipeCreateEvent.AddIngredient -> recipeCreateIngredientUseCase.addIngredient(
                    dialogOpenParams
                )

                is RecipeCreateEvent.EditIngredient -> recipeCreateIngredientUseCase.editIngredient(
                    event, dialogOpenParams
                )

                is RecipeCreateEvent.DeleteStep -> recipeCreateStepUseCase.deleteStep(
                    event
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

                RecipeCreateEvent.AddStep -> recipeCreateStepUseCase.addStep()
                is RecipeCreateEvent.EditImage -> recipeCreateImageUseCase.editImage(
                    dialogOpenParams, event
                )

                is RecipeCreateEvent.EditMainImage -> recipeCreateImageUseCase.editMainImage(
                    state, dialogOpenParams, event
                )

                RecipeCreateEvent.AddManyIngredients -> recipeCreateIngredientUseCase.addManyIngredients()
                is RecipeCreateEvent.DeleteIngredient -> recipeCreateIngredientUseCase.deleteIngredient(
                    event.ingredient
                )

                is RecipeCreateEvent.EditPinnedIngredients -> recipeCreateStepUseCase.editPinnedIngredients(
                    event.recipeStepState, dialogOpenParams
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
                mainViewModel.nav.popBackStack()
            }
        }
        dialogOpenParams.value = params
    }

    private fun done() {
        resultFlow.value = state
        mainViewModel.nav.popBackStack()
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