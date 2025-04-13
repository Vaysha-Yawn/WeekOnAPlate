package week.on.a.plate.screens.base.searchRecipes.logic

import androidx.compose.runtime.MutableState
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.dialogs.forSearchScreen.sortMore.event.SortMoreEvent
import week.on.a.plate.dialogs.forSearchScreen.sortMore.logic.SortMoreDialogViewModel
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.state.ResultSortType
import week.on.a.plate.screens.base.searchRecipes.state.ResultSortingDirection
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class SortingManager @Inject constructor() {
    fun sortMore(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (SearchScreenEvent) -> Unit
    ) {
        val params = SortMoreDialogViewModel.SortMoreDialogParams() { event ->
            when (event) {
                SortMoreEvent.AlphabetNormal -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.Alphabet,
                        ResultSortingDirection.Down
                    )
                )

                SortMoreEvent.AlphabetRevers -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.Alphabet,
                        ResultSortingDirection.Up
                    )
                )

                SortMoreEvent.Close -> {}
                SortMoreEvent.DateFromNewToOld -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.Date,
                        ResultSortingDirection.Down
                    )
                )

                SortMoreEvent.DateFromOldToNew -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.Date,
                        ResultSortingDirection.Up
                    )
                )

                SortMoreEvent.Random -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.Random,
                        ResultSortingDirection.Down
                    )
                )
            }
        }
        dialogOpenParams.value = params
    }

    fun changeSort(type: ResultSortType, direction: ResultSortingDirection, state: SearchUIState) {
        state.resultSortType.value = Pair(type, direction)
        state.resultSearch.value = state.resultSearch.value.sorted(state)
    }
}

fun randomSort(list: List<RecipeView>): List<RecipeView> {
    return if (list.isNotEmpty()) {
        val mutableRecipeViewList = list.toMutableList()
        val listRandom = mutableListOf<RecipeView>()
        while (mutableRecipeViewList.isNotEmpty()) {
            val random = mutableRecipeViewList.random()
            mutableRecipeViewList.remove(random)
            listRandom.add(random)
        }
        listRandom
    } else {
        list
    }
}

fun List<RecipeView>.sorted(state: SearchUIState): List<RecipeView> {
    return when (state.resultSortType.value) {
        Pair(ResultSortType.Date, ResultSortingDirection.Up) ->
            this.sortedWith(compareBy { it.lastEdit })

        Pair(ResultSortType.Alphabet, ResultSortingDirection.Down) -> this.sortedBy { it.name }

        Pair(ResultSortType.Date, ResultSortingDirection.Down) ->
            this.sortedByDescending { it.lastEdit }

        Pair(
            ResultSortType.Alphabet,
            ResultSortingDirection.Up
        ) -> sortedByDescending { it.name }

        Pair(ResultSortType.Random, ResultSortingDirection.Up) -> randomSort(this)

        Pair(ResultSortType.Random, ResultSortingDirection.Down) -> randomSort(this)

        else -> this
    }
}