package week.on.a.plate.core.data.example

import week.on.a.plate.core.data.week.RecipeInMenuView
import week.on.a.plate.core.data.recipe.RecipeStateView
import week.on.a.plate.core.data.week.SelectionView

val dayMenuExample = mutableListOf(
    SelectionView( 0,
        "Нераспределенное",
        mutableListOf(RecipeInMenuView(0, RecipeStateView.Done, shortRecipe, 2))
    ),
    SelectionView( 1,
        "Завтрак",
        mutableListOf(RecipeInMenuView(1, RecipeStateView.InShoppingList, shortRecipe, 4))
    ),
    SelectionView(2, "Обед", mutableListOf(RecipeInMenuView(2, RecipeStateView.Created, shortRecipe, 6))),
    SelectionView(3, "Ужин", mutableListOf(RecipeInMenuView(3, RecipeStateView.Eated, shortRecipe, 8))),
)