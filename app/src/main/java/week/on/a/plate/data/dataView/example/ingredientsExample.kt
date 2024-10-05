package week.on.a.plate.data.dataView.example

import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.startCategoryName

enum class Measure(val s: String) {
    Grams("гр"),
    Milliliters("мл")
}

val ingredients = listOf(
    IngredientCategoryView(
        0,
        startCategoryName,
        listOf()
    ),
    IngredientCategoryView(
        0, "Мясо и птица",
        listOf(
            IngredientView(1, "", "Курица", Measure.Grams.s),
            IngredientView(2, "", "Говядина", Measure.Grams.s),
            IngredientView(3, "", "Свинина", Measure.Grams.s),
            IngredientView(4, "", "Баранина", Measure.Grams.s),
            IngredientView(5, "", "Индейка", Measure.Grams.s),
            IngredientView(6, "", "Куриные грудки", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        1, "Рыба и морепродукты",
        listOf(
            IngredientView(7, "", "Лосось", Measure.Grams.s),
            IngredientView(8, "", "Тунец", Measure.Grams.s),
            IngredientView(9, "", "Креветки", Measure.Grams.s),
            IngredientView(10, "", "Треска", Measure.Grams.s),
            IngredientView(11, "", "Скумбрия", Measure.Grams.s),
            IngredientView(12, "", "Форель", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        2, "Овощи",
        listOf(
            IngredientView(13, "", "Картофель", Measure.Grams.s),
            IngredientView(14, "", "Лук", Measure.Grams.s),
            IngredientView(15, "", "Морковь", Measure.Grams.s),
            IngredientView(16, "", "Чеснок", Measure.Grams.s),
            IngredientView(17, "", "Помидоры", Measure.Grams.s),
            IngredientView(17, "", "Огурцы", Measure.Grams.s),
            IngredientView(17, "", "Зелёный лук", Measure.Grams.s),
            IngredientView(17, "", "Укроп", Measure.Grams.s),
            IngredientView(17, "", "Петрушка", Measure.Grams.s),
            IngredientView(18, "", "Брокколи", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        3, "Фрукты",
        listOf(
            IngredientView(19, "", "Яблоки", Measure.Grams.s),
            IngredientView(20, "", "Бананы", Measure.Grams.s),
            IngredientView(21, "", "Лимоны", Measure.Grams.s),
            IngredientView(22, "", "Апельсины", Measure.Grams.s),
            IngredientView(23, "", "Клубника", Measure.Grams.s),
            IngredientView(24, "", "Груши", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        4, "Зелень и травы",
        listOf(
            IngredientView(25, "", "Петрушка", Measure.Grams.s),
            IngredientView(26, "", "Укроп", Measure.Grams.s),
            IngredientView(27, "", "Базилик", Measure.Grams.s),
            IngredientView(28, "", "Тимьян", Measure.Grams.s),
            IngredientView(29, "", "Орегано", Measure.Grams.s),
            IngredientView(30, "", "Мята", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        5, "Молочные продукты",
        listOf(
            IngredientView(31, "", "Молоко", Measure.Milliliters.s),
            IngredientView(32, "", "Сливочное масло", Measure.Grams.s),
            IngredientView(33, "", "Творог", Measure.Grams.s),
            IngredientView(34, "", "Сыр", Measure.Grams.s),
            IngredientView(35, "", "Сливки", Measure.Milliliters.s),
            IngredientView(36, "", "Сметана", Measure.Milliliters.s),
        ),
    ),
    IngredientCategoryView(
        6, "Зерновые и крупы",
        listOf(
            IngredientView(37, "", "Рис", Measure.Grams.s),
            IngredientView(38, "", "Макароны", Measure.Grams.s),
            IngredientView(39, "", "Овсянка", Measure.Grams.s),
            IngredientView(40, "", "Киноа", Measure.Grams.s),
            IngredientView(41, "", "Гречка", Measure.Grams.s),
            IngredientView(42, "", "Булгур", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        7, "Бобовые",
        listOf(
            IngredientView(43, "", "Фасоль", Measure.Grams.s),
            IngredientView(44, "", "Горох", Measure.Grams.s),
            IngredientView(45, "", "Чечевица", Measure.Grams.s),
            IngredientView(46, "", "Нут", Measure.Grams.s),
            IngredientView(47, "", "Соевые бобы", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        8, "Орехи и семена",
        listOf(
            IngredientView(48, "", "Миндаль", Measure.Grams.s),
            IngredientView(49, "", "Грецкие орехи", Measure.Grams.s),
            IngredientView(50, "", "Фундук", Measure.Grams.s),
            IngredientView(51, "", "Семена подсолнечника", Measure.Grams.s),
            IngredientView(52, "", "Кедровые орешки", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        9, "Специи и приправы",
        listOf(
            IngredientView(53, "", "Соль", Measure.Grams.s),
            IngredientView(54, "", "Черный перец", Measure.Grams.s),
            IngredientView(55, "", "Паприка", Measure.Grams.s),
            IngredientView(56, "", "Корица", Measure.Grams.s),
            IngredientView(57, "", "Имбирь", Measure.Grams.s),
            IngredientView(58, "", "Куркума", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        10, "Соусы и масла",
        listOf(
            IngredientView(59, "", "Оливковое масло", Measure.Milliliters.s),
            IngredientView(60, "", "Соевый соус", Measure.Milliliters.s),
            IngredientView(61, "", "Кетчуп", Measure.Milliliters.s),
            IngredientView(62, "", "Майонез", Measure.Milliliters.s),
            IngredientView(63, "", "Горчица", Measure.Milliliters.s),
        ),
    ),
    IngredientCategoryView(
        11, "Выпечка и десерты",
        listOf(
            IngredientView(64, "", "Мука", Measure.Grams.s),
            IngredientView(65, "", "Сахар", Measure.Grams.s),
            IngredientView(66, "", "Дрожжи", Measure.Grams.s),
            IngredientView(67, "", "Яйца", Measure.Grams.s),
            IngredientView(68, "", "Мёд", Measure.Grams.s),
            IngredientView(69, "", "Хлеб", Measure.Grams.s),
        ),
    ),
    IngredientCategoryView(
        12, "Алкоголь",
        listOf(
            IngredientView(70, "", "Абсент", Measure.Milliliters.s),
            IngredientView(71, "", "Арманьяк", Measure.Milliliters.s),
            IngredientView(72, "", "Вино (сухое белое)", Measure.Milliliters.s),
            IngredientView(73, "", "Водка", Measure.Milliliters.s),
            IngredientView(74, "", "Коньяк", Measure.Milliliters.s),
            IngredientView(75, "", "Виски", Measure.Milliliters.s),
            IngredientView(76, "", "Граппа", Measure.Milliliters.s),
            IngredientView(77, "", "Джин", Measure.Milliliters.s),
            IngredientView(78, "", "Бренди", Measure.Milliliters.s),
            IngredientView(79, "", "Бурбон", Measure.Milliliters.s),
            IngredientView(80, "", "Вермут", Measure.Milliliters.s),
        )
    ),
    IngredientCategoryView(
        13, "Прочее",
        listOf(
            IngredientView(81, "", "Вода", Measure.Milliliters.s),
            IngredientView(82, "", "Бульон", Measure.Milliliters.s),
        )
    ),
)