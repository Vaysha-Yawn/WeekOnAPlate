package week.on.a.plate.screenCreateRecipe.logic

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
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenCreateRecipe.event.RecipeCreateEvent
import week.on.a.plate.screenCreateRecipe.state.RecipeCreateUIState
import week.on.a.plate.screenCreateRecipe.state.RecipeStepState
import week.on.a.plate.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.screenFilters.navigation.FilterDestination
import week.on.a.plate.screenFilters.state.FilterMode
import week.on.a.plate.dialogEditOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogEditOneString.state.EditOneStringUIState
import week.on.a.plate.dialogEditPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.screenFilters.state.FilterEnum
import javax.inject.Inject

@HiltViewModel
class RecipeCreateViewModel @Inject constructor(): ViewModel() {
    lateinit var mainViewModel: MainViewModel
    var state = RecipeCreateUIState()

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
                getTime(){time->
                    state.prepTime.intValue = time
                }
            }

            RecipeCreateEvent.EditAllTime -> {
                getTime(){time->
                    state.allTime.intValue = time
                }
            }

            RecipeCreateEvent.EditTags -> {
                viewModelScope.launch {
                    mainViewModel.nav.navigate(FilterDestination)
                    mainViewModel.filterViewModel.launchAndGet(
                        FilterMode.Multiple, FilterEnum.Tag,
                        Pair(state.tags.value, listOf()), false
                    ) {
                        if (it.tags!=null){
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
                        Position.PositionIngredientView(0,  event.ingredient, 0), false
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
                event.recipeStepState.timer.intValue = 0
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
                        EditOneStringUIState(event.recipeStepState.image.value, "Редактировать ссылку на изображение","Введите новую ссылку на изображение")
                    ) { note ->
                        event.recipeStepState.image.value = note.lowercase()
                    }
                }
            }

            is RecipeCreateEvent.EditTimer -> {
                getTime(){time->
                    event.recipeStepState.timer.intValue = time
                }
            }

            RecipeCreateEvent.AddStep -> {
                viewModelScope.launch {
                    state.steps.value = state.steps.value.toMutableList().apply {
                        this.add(RecipeStepState())
                    }.toList()
                }
            }

            RecipeCreateEvent.EditMainImage -> {
                viewModelScope.launch {
                    val vm = EditOneStringViewModel()
                    vm.mainViewModel = mainViewModel
                    mainViewModel.onEvent(MainEvent.OpenDialog(vm))
                    vm.launchAndGet(
                        EditOneStringUIState( state.photoLink.value,
                        "Редактировать ссылку на изображение","Введите новую ссылку на изображение"
                    )) { note ->
                        state.photoLink.value = note.lowercase()
                    }
                }
            }

            RecipeCreateEvent.AddManyIngredients -> {
                viewModelScope.launch {
                    mainViewModel.nav.navigate(FilterDestination)
                    mainViewModel.filterViewModel.launchAndGet(
                        FilterMode.Multiple, FilterEnum.Ingredient,
                        null, false
                    ) {
                        state.ingredients.value = state.ingredients.value.toMutableList().apply {
                            it.ingredients?.forEach { ingredient->
                                add(IngredientInRecipeView(0, ingredient, "", 0))
                            }
                        }.toList()
                    }
                }
            }

            is RecipeCreateEvent.DeleteIngredient -> {
                state.ingredients.value = state.ingredients.value.toMutableList().apply {
                    this.remove(event.ingredient)
                }
            }
        }
    }

    private fun getTime(use:(Int)->Unit){
        viewModelScope.launch {
            val vm = TimePickViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(use)
        }
    }

    fun start(): Flow<RecipeCreateUIState?> {
        val flow = MutableStateFlow<RecipeCreateUIState?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(
        oldRecipe: RecipeView?,
        use: (RecipeCreateUIState) -> Unit
    ) {
        if (oldRecipe != null) setStateByOldRecipe(oldRecipe)
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
        state.prepTime.intValue = oldRecipe.prepTime
        state.allTime.intValue = oldRecipe.allTime
        state.portionsCount.intValue = oldRecipe.standardPortionsCount
        state.tags.value = oldRecipe.tags
        state.ingredients.value = oldRecipe.ingredients

        val list = mutableListOf<RecipeStepState>()
        oldRecipe.steps.forEach { stepOld ->
            val step = RecipeStepState().also { stepState ->
                with(stepState) {
                    description.value = stepOld.description
                    image.value = stepOld.image
                    timer.intValue = stepOld.timer.toInt()
                }
            }
            list.add(step)
        }
        state.steps.value = list
    }
}