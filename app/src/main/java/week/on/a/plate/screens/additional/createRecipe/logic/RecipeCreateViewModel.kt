package week.on.a.plate.screens.additional.createRecipe.logic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.logic.ExitApplyViewModel
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
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
    private val editTagsUseCase: EditTagsUseCase
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    var state = RecipeCreateUIState()
    private lateinit var resultFlow: MutableStateFlow<RecipeCreateUIState?>

    private lateinit var recipeCreateImageUseCase: RecipeCreateImageUseCase
    private lateinit var recipeCreateTimeUseCase: RecipeCreateTimeUseCase
    private lateinit var recipeCreateStepUseCase: RecipeCreateStepUseCase
    private lateinit var recipeCreateIngredientUseCase: RecipeCreateIngredientUseCase

    fun initWithMainVM(mainViewModel: MainViewModel) {
        recipeCreateImageUseCase = RecipeCreateImageUseCase(mainViewModel, state)
        recipeCreateTimeUseCase = RecipeCreateTimeUseCase(mainViewModel, state)
        recipeCreateStepUseCase = RecipeCreateStepUseCase(mainViewModel, state)
        recipeCreateIngredientUseCase = RecipeCreateIngredientUseCase(mainViewModel, state)
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)
            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent -> onEvent(
                event
            )
        }
    }

    fun onEvent(event: week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent) {
        when (event) {
            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.Close -> mainViewModel.nav.popBackStack()
            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.Done -> done()
            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditTags -> editTagsUseCase(
                mainViewModel,
                state.tags
            )

            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.AddIngredient -> recipeCreateIngredientUseCase.addIngredient()
            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditIngredient -> recipeCreateIngredientUseCase.editIngredient(
                event
            )

            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.DeleteStep -> recipeCreateStepUseCase.deleteStep(
                event
            )

            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.ClearTimer -> recipeCreateTimeUseCase.clearTimer(
                event
            )

            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.DeleteImage -> recipeCreateImageUseCase.deleteImage(
                event
            )

            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditTimer -> recipeCreateTimeUseCase.editTimer(
                event
            )

            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.AddStep -> recipeCreateStepUseCase.addStep()
            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditImage -> recipeCreateImageUseCase.editImage(
                event
            )

            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditMainImage -> recipeCreateImageUseCase.editMainImage(
                event
            )

            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.AddManyIngredients -> recipeCreateIngredientUseCase.addManyIngredients()
            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.DeleteIngredient -> recipeCreateIngredientUseCase.deleteIngredient(
                event.ingredient
            )

            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditPinnedIngredients -> recipeCreateStepUseCase.editPinnedIngredients(
                event.recipeStepState
            )

            is week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditRecipeDuration -> recipeCreateTimeUseCase.editRecipeDuration()
            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.OpenDialogExitApplyFromCreateRecipe -> openDialogExitApplyFromCreateRecipe()
        }
    }

    private fun openDialogExitApplyFromCreateRecipe() {
        ExitApplyViewModel.launch(mainViewModel) { event ->
            if (event == ExitApplyEvent.Exit) {
                mainViewModel.nav.popBackStack()
            }
        }
    }

    private fun done() {
        resultFlow.value = state
        //mainViewModel.cookPlannerViewModel.update()
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
                //mainViewModel.cookPlannerViewModel.update()
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