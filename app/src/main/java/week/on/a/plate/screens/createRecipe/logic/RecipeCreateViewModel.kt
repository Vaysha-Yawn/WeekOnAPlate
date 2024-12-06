package week.on.a.plate.screens.createRecipe.logic

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
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
import week.on.a.plate.dialogs.chooseHowImagePick.logic.ChooseHowImagePickViewModel
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
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
import week.on.a.plate.mainActivity.logic.imageFromGallery.getSavedPicture
import javax.inject.Inject

@HiltViewModel
class RecipeCreateViewModel @Inject constructor() : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    var state = RecipeCreateUIState()
    var isFirstTimeline:Boolean = true
    private var stateTimeline = RecipeTimelineUIState(
        mutableStateOf(listOf())
    )

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

            RecipeCreateEvent.Done -> done()

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

            is RecipeCreateEvent.EditTimer -> {
                getTime("На сколько поставить таймер?") { time ->
                    event.recipeStepState.timer.longValue = time
                }
            }

            RecipeCreateEvent.AddStep -> {
                viewModelScope.launch {
                    state.steps.value = state.steps.value.toMutableList().apply {
                        this.add(RecipeStepState(0))
                    }.toList()
                }
            }

            is RecipeCreateEvent.EditImage -> getImage(event.recipeStepState.image.value) { nameImage ->
                viewModelScope.launch {
                    event.recipeStepState.image.value = nameImage
                    if (!nameImage.startsWith("http")){
                        val picture = getSavedPicture(event.context, nameImage)
                        event.recipeStepState.imageContainer.value = picture?.asImageBitmap()
                    }
                }
            }

            is RecipeCreateEvent.EditMainImage -> getImage(state.photoLink.value) { nameImage ->
                viewModelScope.launch {
                    state.photoLink.value = nameImage
                    if (!nameImage.startsWith("http")){
                        val picture = getSavedPicture(event.context, nameImage)
                        state.mainImageContainer.value = picture?.asImageBitmap()
                    }
                }
            }

            RecipeCreateEvent.AddManyIngredients -> addManyIngredients()
            is RecipeCreateEvent.DeleteIngredient -> deleteIngredient(event.ingredient)
            RecipeCreateEvent.SetTimeline -> setTimeline()
            is RecipeCreateEvent.EditPinnedIngredients -> editPinnedIngredients(event.recipeStepState)
            is RecipeCreateEvent.EditStepDuration -> {
               // event.stepState.duration = event.time.toLong()
            }
            is RecipeCreateEvent.SetCustomDuration -> {
                getTime("Длительность шага"){time->
                   // event.state.duration = time
                }
            }
        }
    }

    private fun getImage(oldValue: String?, use: (String) -> Unit) {
        val vmChoose = ChooseHowImagePickViewModel()
        vmChoose.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vmChoose))
        viewModelScope.launch {
            vmChoose.launchAndGet(oldValue, use)
        }
    }

    private fun addManyIngredients() {
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

    private fun deleteIngredient(ingredient: IngredientInRecipeView) {
        val ind = ingredient.ingredientView.ingredientId
        state.steps.value.forEach {
            if (it.pinnedIngredientsInd.value.contains(ind)) {
                it.pinnedIngredientsInd.value =
                    it.pinnedIngredientsInd.value.toMutableList().apply {
                        remove(ind)
                    }
            }
        }
        state.ingredients.value = state.ingredients.value.toMutableList().apply {
            this.remove(ingredient)

        }
    }

    private fun done() {
        resultFlow.value = state
        mainViewModel.menuViewModel.updateWeek()
        mainViewModel.cookPlannerViewModel.update()
        mainViewModel.nav.popBackStack()
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
                stateTimeline, isFirstTimeline
            ) { stateTL ->
                stateTimeline = stateTL
                state.steps.value.forEach { step ->
                    val stepState = stateTL.allUISteps.value.find { it.id == step.id }!!
                  //  step.start = stepState.start.value
                  //  step.duration = stepState.duration.value
                }
                if (isFirstTimeline) {
                    isFirstTimeline = false
                }
            }
        }
    }

    private fun updateTimelineState() {
        /*stateTimeline.allUISteps.value = state.steps.value.map {
            StepTimelineData(
                id = it.id,
                description = it.description.value,
                start = mutableLongStateOf(it.start),
                duration = mutableLongStateOf(it.duration)
            )
        }*/
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
        if (oldRecipe != null) setStateByOldRecipe(oldRecipe) else state = RecipeCreateUIState()

        isFirstTimeline = oldRecipe==null

        state.isForCreate.value = isForCreate
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
                mainViewModel.cookPlannerViewModel.update()
            }
        }
    }

    fun setStateByOldRecipe(oldRecipe: RecipeView) {
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