package week.on.a.plate.screens.createRecipe.logic

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.createRecipe.state.RecipeStepState
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode
import week.on.a.plate.screens.recipeTimeline.navigation.RecipeTimelineDestination
import week.on.a.plate.screens.recipeTimeline.state.RecipeTimelineUIState
import week.on.a.plate.screens.recipeTimeline.state.StepTimelineData
import javax.inject.Inject

@HiltViewModel
class RecipeCreateViewModel @Inject constructor() : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    var state = RecipeCreateUIState()
    private var stateTimeline = RecipeTimelineUIState(
            mutableStateOf(listOf()), state.allTime.longValue.toInt())

    private lateinit var resultFlow: MutableStateFlow<RecipeCreateUIState?>

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)
            else -> onEvent(event)
        }
    }

    fun onEvent(event: RecipeCreateEvent) {
        when (event) {
            RecipeCreateEvent.Close -> mainViewModel.nav.popBackStack()

            RecipeCreateEvent.Done -> {
                resultFlow.value = state
                mainViewModel.nav.popBackStack()
            }

            RecipeCreateEvent.EditActiveTime -> {
                getTime("Укажите активное время приготовления") { time ->
                    state.prepTime.longValue = time
                }
            }

            RecipeCreateEvent.EditAllTime -> {
                getTime("Укажите полное время приготовления") { time ->
                    state.allTime.longValue = time
                }
            }

            RecipeCreateEvent.EditTags -> {
                viewModelScope.launch {
                    mainViewModel.nav.navigate(FilterDestination)
                    mainViewModel.filterViewModel.launchAndGet(
                        FilterMode.Multiple, FilterEnum.Tag,
                        Pair(state.tags.value, listOf()), false
                    ) {
                        if (it.tags != null) {
                            state.tags.value = it.tags
                        }
                    }
                }
            }

            RecipeCreateEvent.AddIngredient -> {
                viewModelScope.launch {
                    val vm = EditPositionIngredientViewModel()
                    vm.mainViewModel = mainViewModel
                    vm.launchAndGet(
                        null, true
                    ) { ingredient ->
                        state.ingredients.value = state.ingredients.value.toMutableList().apply {
                            this.add(ingredient.ingredient)
                        }.toList()
                    }
                }
            }

            is RecipeCreateEvent.EditIngredient -> {
                viewModelScope.launch {
                    val vm = EditPositionIngredientViewModel()
                    vm.mainViewModel = mainViewModel
                    vm.launchAndGet(
                        Position.PositionIngredientView(0, event.ingredient, 0), false
                    ) { ingredient ->
                        state.ingredients.value = state.ingredients.value.toMutableList().apply {
                            val index = this.indexOf(event.ingredient)
                            this.remove(event.ingredient)
                            this.add(index, ingredient.ingredient)
                        }.toList()
                    }
                }
            }

            is RecipeCreateEvent.DeleteStep -> {
                state.steps.value = state.steps.value.toMutableList().apply {
                    this.remove(event.recipeStepState)
                }.toList()
            }

            is RecipeCreateEvent.ClearTime -> {
                event.recipeStepState.timer.longValue = 0
            }

            is RecipeCreateEvent.DeleteImage -> {
                event.recipeStepState.image.value = ""
            }

            is RecipeCreateEvent.EditImage -> {
                viewModelScope.launch {
                    val vm = EditOneStringViewModel()
                    vm.mainViewModel = mainViewModel
                    mainViewModel.onEvent(MainEvent.OpenDialog(vm))
                    vm.launchAndGet(
                        EditOneStringUIState(
                            event.recipeStepState.image.value,
                            "Редактировать ссылку на изображение",
                            "Введите новую ссылку на изображение"
                        )
                    ) { note ->
                        event.recipeStepState.image.value = note.lowercase()
                    }
                }
            }

            is RecipeCreateEvent.EditTimer -> {
                getTime("На сколько поставить таймер?") { time ->
                    event.recipeStepState.timer.longValue = time
                }
            }

            RecipeCreateEvent.AddStep -> {
                viewModelScope.launch {
                    state.steps.value = state.steps.value.toMutableList().apply {
                        this.add(RecipeStepState(0, 0, 5))
                    }.toList()
                }
            }

            RecipeCreateEvent.EditMainImage -> {
                viewModelScope.launch {
                    val vm = EditOneStringViewModel()
                    vm.mainViewModel = mainViewModel
                    mainViewModel.onEvent(MainEvent.OpenDialog(vm))
                    vm.launchAndGet(
                        EditOneStringUIState(
                            state.photoLink.value,
                            "Редактировать ссылку на изображение",
                            "Введите новую ссылку на изображение"
                        )
                    ) { note ->
                        state.photoLink.value = note.lowercase()
                    }
                }
            }

            RecipeCreateEvent.AddManyIngredients -> {
                viewModelScope.launch {
                    mainViewModel.nav.navigate(FilterDestination)
                    val ingredientsOld = state.ingredients.value.map { it.ingredientView }
                    mainViewModel.filterViewModel.launchAndGet(
                        FilterMode.Multiple, FilterEnum.Ingredient,
                        Pair(listOf(), ingredientsOld), false
                    ) { filterRes ->
                        val ingredientsNew = filterRes.ingredients ?: return@launchAndGet
                        val listToAdd = ingredientsNew.toMutableList().apply {
                            removeAll(ingredientsOld)
                        }.toList()
                        val listToDelete = ingredientsOld.toMutableList().apply {
                            removeAll(ingredientsNew)
                        }.toList()

                        state.ingredients.value = state.ingredients.value.toMutableList().apply {
                            listToAdd.forEach { ingredient ->
                                add(IngredientInRecipeView(0, ingredient, "", 0))
                            }
                        }.toList()

                        state.ingredients.value = state.ingredients.value.toMutableList().apply {
                            listToDelete.forEach { ingredient ->
                                val t =
                                    state.ingredients.value.find { it.ingredientView.ingredientId == ingredient.ingredientId }
                                remove(t)
                            }
                        }.toList()
                    }
                }
            }

            is RecipeCreateEvent.DeleteIngredient -> {
                val ind = event.ingredient.ingredientView.ingredientId
                state.steps.value.forEach {
                    if (it.pinnedIngredientsInd.value.contains(ind)) {
                        it.pinnedIngredientsInd.value =
                            it.pinnedIngredientsInd.value.toMutableList().apply {
                                remove(ind)
                            }
                    }
                }
                state.ingredients.value = state.ingredients.value.toMutableList().apply {
                    this.remove(event.ingredient)
                }
            }

            RecipeCreateEvent.SetTimeline -> setTimeline()
            is RecipeCreateEvent.EditPinnedIngredients -> editPinnedIngredients(event.recipeStepState)
        }
    }

    private fun editPinnedIngredients(recipeStepState: RecipeStepState) {
        viewModelScope.launch {
            val vm = ChooseIngredientsForStepViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                state.ingredients.value,
                recipeStepState.pinnedIngredientsInd.value
            ) { newList ->
                recipeStepState.pinnedIngredientsInd.value = newList
            }
        }
    }

    private fun setTimeline() {
        mainViewModel.onEvent(MainEvent.Navigate(RecipeTimelineDestination))
        viewModelScope.launch {
            updateTimelineState()
            mainViewModel.recipeTimelineViewModel.launchAndGet(
                stateTimeline
            ) {stateTL->
                stateTimeline = stateTL
                state.steps.value.forEach { step ->
                    val stepState = stateTL.allUISteps.value.find { it.id == step.id }!!
                    step.start = stepState.start.value
                    step.duration = stepState.duration.value
                }
            }
        }
    }

    private fun updateTimelineState() {
        stateTimeline.allUISteps.value = state.steps.value.map {
            StepTimelineData(
                id = it.id,
                description = it.description.value,
                start = mutableLongStateOf(it.start),
                duration = mutableLongStateOf(it.duration)
            )
        }

        stateTimeline.plannedAllTime = state.allTime.longValue.toInt()
    }

    private fun getTime(title: String, use: (Long) -> Unit) {
        viewModelScope.launch {
            val vm = TimePickViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(title, use)
        }
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
        if (oldRecipe != null) setStateByOldRecipe(oldRecipe)
        state.isForCreate.value = isForCreate
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

    fun setStateByOldRecipe(oldRecipe: RecipeView) {
        state.source.value = oldRecipe.link
        state.photoLink.value = oldRecipe.img
        state.name.value = oldRecipe.name
        state.description.value = oldRecipe.description
        state.prepTime.longValue = oldRecipe.prepTime
        state.allTime.longValue = oldRecipe.allTime
        state.portionsCount.intValue = oldRecipe.standardPortionsCount
        state.tags.value = oldRecipe.tags
        state.ingredients.value = oldRecipe.ingredients


        val list = mutableListOf<RecipeStepState>()
        oldRecipe.steps.forEach { stepOld ->
            val step =
                RecipeStepState(stepOld.id, stepOld.start, stepOld.duration).also { stepState ->
                    with(stepState) {
                        description.value = stepOld.description
                        image.value = stepOld.image
                        timer.longValue = stepOld.timer
                        pinnedIngredientsInd.value = stepOld.ingredientsPinnedId
                    }
                }
            list.add(step)
        }
        state.steps.value = list
    }
}