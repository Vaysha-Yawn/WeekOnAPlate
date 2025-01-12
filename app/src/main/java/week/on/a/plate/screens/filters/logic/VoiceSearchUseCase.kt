package week.on.a.plate.screens.filters.logic

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterUIState
import javax.inject.Inject

class VoiceSearchUseCase @Inject constructor(private val searchUseCase: SearchUseCase) {
    operator fun invoke(
        context: Context,
        mainViewModel: MainViewModel,
        onEvent: (FilterEvent) -> Unit,
        searchText: MutableState<String>,
        scope: CoroutineScope,
        state: FilterUIState
    ) {
        mainViewModel.onEvent(MainEvent.VoiceToText(context) { strings: ArrayList<String>? ->
            if (strings == null) return@VoiceToText
            scope.launch {
                val searchedList = strings.getOrNull(0)?.split(" ") ?: return@launch

                val listIngredientView = mutableListOf<IngredientView>()
                val listTags = mutableListOf<RecipeTagView>()

                searchedList.forEach {
                    val res = searchUseCase.getAllSearch(
                        it,
                        state.filterEnum.value,
                        state.allTagsCategories.value,
                        state.allIngredientsCategories.value
                    )
                    if (res.tags != null) listTags.addAll(res.tags)
                    if (res.ingredients != null) listIngredientView.addAll(res.ingredients)
                }

                if (listIngredientView.isEmpty() && listTags.isEmpty()) {
                    searchText.value = strings.joinToString()
                    return@launch
                }

                FilterVoiceApplyViewModel.launch(
                    listTags, listIngredientView,
                    mainViewModel
                ) { stateApply ->
                    stateApply.selectedTags.value.forEach {
                        onEvent(FilterEvent.SelectTag(it))
                    }
                    stateApply.selectedIngredients.value.forEach {
                        onEvent(FilterEvent.SelectIngredient(it))
                    }
                }
            }
        })
    }
}