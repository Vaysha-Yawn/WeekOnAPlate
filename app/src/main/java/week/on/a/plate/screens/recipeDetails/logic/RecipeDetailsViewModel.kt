package week.on.a.plate.screens.recipeDetails.logic

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.data.dataView.example.emptyRecipe
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.menu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screens.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.specifySelection.navigation.SpecifySelectionDestination
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = RecipeDetailsState()
    private val _recipeFlow: MutableStateFlow<RecipeView> = MutableStateFlow(emptyRecipe)
    val recipeFlow: StateFlow<RecipeView> = _recipeFlow

    fun onEvent(event: RecipeDetailsEvent) {
        when (event) {
            RecipeDetailsEvent.AddToCart -> addToCart()
            RecipeDetailsEvent.AddToMenu -> addToMenu()
            RecipeDetailsEvent.Back -> mainViewModel.nav.popBackStack()
            RecipeDetailsEvent.Edit -> editRecipe()
            RecipeDetailsEvent.MinusPortionsView -> minusPortionsView()
            RecipeDetailsEvent.PlusPortionsView -> plusPortionsView()
            RecipeDetailsEvent.SwitchFavorite -> switchFavorite()
            RecipeDetailsEvent.Delete -> delete()
            is RecipeDetailsEvent.Share -> share(event.context)
        }
    }

    private fun share(context: Context) {
        val text = recipeToText(state.recipe.value)
        val intent =Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val chooserIntent = Intent.createChooser(intent, "Поделиться через:")
        context.startActivity(chooserIntent)
    }

    private fun recipeToText(recipeView: RecipeView):String{
        var text = ""
        text+= "Рецепт: "+recipeView.name
        text+= "\n"
        text+= "\n"
        text+= "Активное время приготовления: " + recipeView.prepTime.timeToString()
        text+= "\n"
        text+= "Общее время приготовления: " + recipeView.allTime.timeToString()
        text+= "\n"
        text+= "Колличество порций: " + recipeView.standardPortionsCount
        text+= "\n"
        text+= "\n"
        text+= "Ингредиенты:"
        for (ingredient in recipeView.ingredients){
            text+= "\n"
            val tet = getIngredientCountAndMeasure1000(ingredient.count, ingredient.ingredientView.measure)
            text+= "- "+ingredient.ingredientView.name + " " + tet.first + " " + tet.second
        }
        text+= "\n"
        text+= "\n"
        text+= "Приготовление:"
        for ((index, step) in recipeView.steps.withIndex()){
            text+= "\n"
            text+= (index+1).toString() +". "+ step.description
        }
        text+= "\n"
        text+= "\n"
        text+= "Источник:"+ recipeView.link
        text+= "\n"
        text+= "\n"
        text+= "Экспортировано из приложения \"Неделя на тарелке\" - книга рецептов, составление меню и список покупок. Только для самых любимых и проверенных рецептов, остальным вход воспрещён! (\u2060灬\u2060º\u2060‿\u2060º\u2060灬\u2060)\u2060♡"
        return text
    }

    private fun addToCart() {
        if (state.recipe.value.ingredients.isNotEmpty() && state.recipe.value.ingredients.find { it.count > 0 } != null) {
            viewModelScope.launch {
                val listCopy = state.recipe.value.ingredients.toList()
                listCopy.forEachIndexed { index, ingredientInRecipeView ->
                    ingredientInRecipeView.count = state.ingredientsCounts.value[index]
                }
                mainViewModel.nav.navigate(InventoryDestination)
                mainViewModel.inventoryViewModel.launchAndGet(listCopy)
            }
        }
    }

    private fun plusPortionsView() {
        state.currentPortions.intValue = state.currentPortions.intValue.plus(1)
        updIngredientsCount()
    }

    fun updIngredientsCount() {
        val newCountPortions = state.currentPortions.intValue
        val startCount = state.recipe.value.standardPortionsCount
        if (state.recipe.value.ingredients.size == state.ingredientsCounts.value.size && state.recipe.value.ingredients.isNotEmpty()) {
            state.ingredientsCounts.value = state.ingredientsCounts.value.toMutableList().apply {
                this.forEachIndexed { index, _ ->
                    val startIngredientCount = state.recipe.value.ingredients[index].count
                    if (startIngredientCount > 0) {
                        this[index] = (startIngredientCount.toFloat() / startCount.toFloat() * newCountPortions.toFloat()).toInt()
                    }
                }
            }.toList()
        }
    }

    private fun minusPortionsView() {
        if (state.currentPortions.intValue > 1) {
            state.currentPortions.intValue = state.currentPortions.intValue.minus(1)
            updIngredientsCount()
        }
    }

    private fun delete() {
        viewModelScope.launch {
            val vm = mainViewModel.deleteApplyViewModel
            val mes = "Вы уверены, что хотите удалить этот рецепт?\n" +
                    "Внимание, при удалении рецепта так же удалятся все позиции с ним в меню.\n" +
                    "Это действие нельзя отменить."
            mainViewModel.nav.navigate(DeleteApplyDestination)
            vm.launchAndGet(message = mes) { event ->
                if (event == DeleteApplyEvent.Apply) {
                    recipeRepository.delete(state.recipe.value.id)
                    mainViewModel.onEvent(MainEvent.NavigateBack)
                }
            }
        }
    }

    private fun switchFavorite() {
        viewModelScope.launch {
            recipeRepository.updateRecipeFavorite(
                state.recipe.value,
                !state.recipe.value.inFavorite
            )
            update()
        }
    }

    private fun addToMenu() {
        viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            mainViewModel.nav.navigate(SpecifySelectionDestination)
            vm.launchAndGet() { res ->
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(
                        week.on.a.plate.screens.menu.event.ActionWeekMenuDB.AddRecipePositionInMenuDB(
                            res.selId,
                            Position.PositionRecipeView(
                                0,
                                RecipeShortView(
                                    state.recipe.value.id,
                                    state.recipe.value.name,
                                    state.recipe.value.img
                                ),
                                res.portions,
                                res.selId
                            )
                        )
                    )
                }
            }
        }
    }

    private fun editRecipe() {
        viewModelScope.launch {
            val vm = mainViewModel.recipeCreateViewModel
            mainViewModel.nav.navigate(RecipeCreateDestination)
            vm.launchAndGet(state.recipe.value, false) { recipe ->
                val newRecipe = RecipeView(
                    id = 0,
                    name = recipe.name.value,
                    description = recipe.description.value,
                    img = recipe.photoLink.value,
                    tags = recipe.tags.value,
                    prepTime = recipe.prepTime.intValue,
                    allTime = recipe.allTime.intValue,
                    standardPortionsCount = recipe.portionsCount.intValue,
                    ingredients = recipe.ingredients.value,
                    steps = recipe.steps.value.map {it->
                        RecipeStepView(
                            0,
                            it.description.value,
                            it.image.value,
                            it.timer.intValue.toLong(), it.duration.value
                        )
                    },
                    link = recipe.source.value, false, LocalDateTime.now()
                )
                viewModelScope.launch {
                    recipeRepository.updateRecipe(state.recipe.value, newRecipe)
                    update()
                }
            }
        }
    }

    fun launch(recipeId: Long, portionsCount: Int? = null) {
        viewModelScope.launch {
            _recipeFlow.value = recipeRepository.getRecipe(recipeId)
            val list = mutableListOf<Int>()
            _recipeFlow.value.ingredients.forEach { ingredientInRecipeView ->
                val count = ingredientInRecipeView.count
                list.add(count)
            }
            state.ingredientsCounts.value = list
            state.currentPortions.intValue =
                portionsCount ?: _recipeFlow.value.standardPortionsCount
        }
    }

    fun update() {
        launch(state.recipe.value.id)
    }

}