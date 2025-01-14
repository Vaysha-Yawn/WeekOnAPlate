package week.on.a.plate.screens.searchRecipes.logic

import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.dialogs.sortMore.event.SortMoreEvent
import week.on.a.plate.dialogs.sortMore.logic.SortMoreViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.searchRecipes.state.ResultSortType
import week.on.a.plate.screens.searchRecipes.state.ResultSortingDirection
import week.on.a.plate.screens.searchRecipes.state.SearchUIState
import javax.inject.Inject

class SortingManager @Inject constructor() {
    fun sortMore(mainViewModel:MainViewModel, onEvent:(SearchScreenEvent)->Unit) {
        SortMoreViewModel.launch(mainViewModel) { event ->
            when (event) {
                SortMoreEvent.AlphabetNormal -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.alphabet,
                        ResultSortingDirection.down
                    )
                )

                SortMoreEvent.AlphabetRevers -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.alphabet,
                        ResultSortingDirection.up
                    )
                )

                SortMoreEvent.Close -> {}
                SortMoreEvent.DateFromNewToOld -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.date,
                        ResultSortingDirection.down
                    )
                )

                SortMoreEvent.DateFromOldToNew -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.date,
                        ResultSortingDirection.up
                    )
                )

                SortMoreEvent.Random -> onEvent(
                    SearchScreenEvent.ChangeSort(
                        ResultSortType.random,
                        ResultSortingDirection.down
                    )
                )
            }
        }
    }

    fun changeSort(type: ResultSortType, direction: ResultSortingDirection, state:SearchUIState) {
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

fun List<RecipeView>.sorted( state:SearchUIState): List<RecipeView> {
    return when (state.resultSortType.value) {
        Pair(ResultSortType.date, ResultSortingDirection.up) ->
            this.sortedWith(compareBy { it.lastEdit })

        Pair(ResultSortType.alphabet, ResultSortingDirection.down) -> this.sortedBy { it.name }

        Pair(ResultSortType.date, ResultSortingDirection.down) ->
            this.sortedByDescending { it.lastEdit }

        Pair(
            ResultSortType.alphabet,
            ResultSortingDirection.up
        ) -> sortedByDescending { it.name }

        Pair(ResultSortType.random, ResultSortingDirection.up) -> randomSort(this)

        Pair(ResultSortType.random, ResultSortingDirection.down) -> randomSort(this)

        else -> this
    }
}