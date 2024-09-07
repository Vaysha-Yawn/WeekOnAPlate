package week.on.a.plate.core.data.example


import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.SelectionView

val dayMenuExample = mutableListOf(
    SelectionView(0, "Нераспределенное",
        mutableListOf(Position.PositionRecipeView(0, shortRecipe, 2))
    ),
    SelectionView(1, "Завтрак",
        mutableListOf(Position.PositionRecipeView(1, shortRecipe, 4))
    ),
    SelectionView(2, "Обед",
        mutableListOf(Position.PositionRecipeView(2, shortRecipe, 6))
    ),
    SelectionView(3, "Ужин",
        mutableListOf(Position.PositionRecipeView(3,shortRecipe, 8))
    ),
)

val emptyDay = mutableListOf(
    SelectionView(0, "Нераспределенное", mutableListOf()),
    SelectionView(1, "Завтрак", mutableListOf()),
    SelectionView(2, "Обед", mutableListOf()),
    SelectionView(3, "Ужин", mutableListOf()),
)