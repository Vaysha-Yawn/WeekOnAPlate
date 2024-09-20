package week.on.a.plate.core.data.example

import week.on.a.plate.core.data.recipe.IngredientCategoryView
import week.on.a.plate.core.data.recipe.IngredientView

enum class Measure(name: String) {
    Grams("Граммы"),
    Milliliters("Миллилитры")
}

val ingredients = listOf(
    IngredientCategoryView(
        0, "Овощи",
        listOf(
            IngredientView(0, "", "Томаты", Measure.Grams.name),
            IngredientView(0, "", "Лук репчатый", Measure.Grams.name),
            IngredientView(0, "", "Морковь", Measure.Grams.name),
            IngredientView(0, "", "Чеснок", Measure.Grams.name),
            IngredientView(0, "", "Картофель", Measure.Grams.name),
            IngredientView(0, "", "Свекла", Measure.Grams.name),
        ),
    ),
    IngredientCategoryView(
        1, "Жидкости",
        listOf(
            IngredientView(0, "", "Молоко", Measure.Milliliters.name),
            IngredientView(0, "", "Кефир", Measure.Milliliters.name),
            IngredientView(0, "", "Вода", Measure.Milliliters.name),
        ),),
    IngredientCategoryView(
        1, "Мучное",
        listOf(
            IngredientView(0, "", "Хлеб", Measure.Grams.name),
            IngredientView(0, "", "Спагетти", Measure.Grams.name),
            IngredientView(0, "", "Макароны", Measure.Grams.name),
        ),),
)