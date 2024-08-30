package week.on.a.plate.core.data.example

import week.on.a.plate.core.data.week.RecipeInMenu
import week.on.a.plate.core.data.recipe.RecipeState
import week.on.a.plate.core.data.week.SelectionInDayData

val dayMenuExample = mutableListOf(
    SelectionInDayData(
        "Нераспределенное",
        mutableListOf(RecipeInMenu(0, RecipeState.Done, recipeTom, 2))
    ),
    SelectionInDayData(
        "Завтрак",
        mutableListOf(RecipeInMenu(1, RecipeState.InShoppingList, recipeTom, 4))
    ),
    SelectionInDayData("Обед", mutableListOf(RecipeInMenu(2, RecipeState.Created, recipeTom, 6))),
    SelectionInDayData("Ужин", mutableListOf(RecipeInMenu(3, RecipeState.Eated, recipeTom, 8))),
)