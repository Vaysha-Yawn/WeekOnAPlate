package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class SelectTagsUseCase @Inject constructor() {
    operator fun invoke(
        recipeTagView: RecipeTagView, state: SearchUIState
    ) {
        state.selectedTags.value = state.selectedTags.value.toMutableList().apply {
            if (!this.contains(recipeTagView)) {
                this.add(recipeTagView)
            }
        }
    }
}