package week.on.a.plate.data.dataView.example

import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView

val tags = listOf(
    TagCategoryView(0, "Особое", listOf(RecipeTagView(0, "Избранное"))),
    TagCategoryView(
        1,
        "По приёмам пищи",
        listOf(
            RecipeTagView(1, "Завтрак"),
            RecipeTagView(2, "Обед"),
            RecipeTagView(3, "Ужин")
        )
    ),
    TagCategoryView(
        2,
        "Кухни мира",
        listOf(
            RecipeTagView(4, "Русская"),
            RecipeTagView(5, "Американская"),
            RecipeTagView(6, "Азиатская")
        )
    ),
    TagCategoryView(
        3,
        "Преимущества",
        listOf(
            RecipeTagView(7, "Лёгкий"),
            RecipeTagView(8, "Быстрый"),
            RecipeTagView(9, "Недорогой")
        )
    ),
    TagCategoryView(
        4,
        "По способу приготовления ",
        listOf(
            RecipeTagView(10, "В духовке "),
            RecipeTagView(11, "Жарить на сковородке"),
            RecipeTagView(12, "В мультиварке"),
            RecipeTagView(13, "На пару "),
        )
    ),
)