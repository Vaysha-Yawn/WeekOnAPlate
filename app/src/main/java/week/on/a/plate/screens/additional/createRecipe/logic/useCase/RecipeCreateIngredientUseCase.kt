package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.navigation.FilterNavParams
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterResult
import javax.inject.Inject

class RecipeCreateIngredientUseCase @Inject constructor() {
    suspend fun addIngredient(
        dialogOpenParams: MutableStateFlow<DialogOpenParams?>,
        state: RecipeCreateUIState
    ) {
        val params = EditPositionIngredientViewModel.EditPositionIngredientDialogParams(
            null,
            true
        ) { ingredient ->
            state.ingredients.value = state.ingredients.value.toMutableList().apply {
                this.add(ingredient.ingredient)
            }.toList()
        }
        dialogOpenParams.emit(params)
    }

    suspend fun editIngredient(
        event: RecipeCreateEvent.EditIngredient,
        dialogOpenParams: MutableStateFlow<DialogOpenParams?>, state: RecipeCreateUIState
    ) {
        val params = EditPositionIngredientViewModel.EditPositionIngredientDialogParams(
            Position.PositionIngredientView(
                0,
                event.ingredient,
                0
            ), false
        ) { ingredient ->
            state.ingredients.value = state.ingredients.value.toMutableList().apply {
                val index = this.indexOf(event.ingredient)
                this.remove(event.ingredient)
                this.add(index, ingredient.ingredient)
            }.toList()
        }

        dialogOpenParams.emit(params)
    }

    fun deleteIngredient(ingredient: IngredientInRecipeView, state: RecipeCreateUIState) {
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

    fun addManyIngredients(state: RecipeCreateUIState, onEvent: (MainEvent) -> Unit) {
        val ingredientsOld = state.ingredients.value.map { it.ingredientView }
        val params = FilterNavParams(
            FilterMode.Multiple, FilterEnum.Ingredient,
            Pair(listOf(), ingredientsOld), false
        ) { filterRes ->
            state.ingredients.value =
                applyToStateAddManyIngredients(filterRes, ingredientsOld, state.ingredients.value)
        }
        onEvent(MainEvent.Navigate(FilterDestination, params))
    }

    private fun applyToStateAddManyIngredients(
        filterRes: FilterResult,
        ingredientsOld: List<IngredientView>,
        list: List<IngredientInRecipeView>
    ): List<IngredientInRecipeView> {

        val ingredientsNew = filterRes.ingredients ?: return list

        val listToAdd = ingredientsNew.toMutableList().apply {
            removeAll(ingredientsOld)
        }.toList()

        val listToDelete = ingredientsOld.toMutableList().apply {
            removeAll(ingredientsNew)
        }.toList()

        val mutableList = list.toMutableList()

        listToAdd.forEach { ingredient ->
            mutableList.add(IngredientInRecipeView(0, ingredient, "", 0))
        }

        listToDelete.forEach { ingredient ->
            val t = list.find { it.ingredientView.ingredientId == ingredient.ingredientId }
            mutableList.remove(t)
        }

        return mutableList.toList()
    }


}