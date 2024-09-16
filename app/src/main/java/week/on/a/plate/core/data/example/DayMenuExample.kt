package week.on.a.plate.core.data.example


import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.SelectionView


val positionRecipeExample = Position.PositionRecipeView(0, shortRecipe, 2, 0)

val ingredientTomato = IngredientView(0, "", "Помидор", "штук")

val positionIngredientExample =
    Position.PositionIngredientView(0, IngredientInRecipeView(0, ingredientTomato, "Целые", 6.0), 0)

val positionNoteExample = Position.PositionNoteView(0, "Завтракаю на работе", 0)

val positionDraftExample = Position.PositionDraftView(
    0, listOf(
        RecipeTagView(0, "Без тепловой обработки", false),
        RecipeTagView(0, "Салаты", true),
    ),
    listOf(ingredientTomato), 0
)

val dayMenuExample = mutableListOf(
    SelectionView(
        0, CategoriesSelection.NonPosed.fullName,
        mutableListOf(
            positionRecipeExample,
            positionNoteExample,
            positionDraftExample,
            positionIngredientExample
        )
    ),
    SelectionView(
        1, CategoriesSelection.Breakfast.fullName,
        mutableListOf(positionNoteExample)
    ),
    SelectionView(
        2, CategoriesSelection.Lunch.fullName,
        mutableListOf(positionDraftExample)
    ),
    SelectionView(
        3, CategoriesSelection.Dinner.fullName,
        mutableListOf(positionIngredientExample)
    ),
)
val emptyDayWithoutSel = mutableListOf<SelectionView>()
val emptyDay = mutableListOf(
    SelectionView(0, CategoriesSelection.NonPosed.fullName, mutableListOf()),
    SelectionView(1, CategoriesSelection.Breakfast.fullName, mutableListOf()),
    SelectionView(2, CategoriesSelection.Lunch.fullName, mutableListOf()),
    SelectionView(3, CategoriesSelection.Dinner.fullName, mutableListOf()),
)