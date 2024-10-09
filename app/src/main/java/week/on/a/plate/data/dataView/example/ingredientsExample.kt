package week.on.a.plate.data.dataView.example

import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.startCategoryName

enum class Measure(val small: String, val big:String) {
    Grams("гр", "кг"),
    Milliliters("мл", "л")
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
            IngredientView(1, "", "Курица", Measure.Grams.small),
            IngredientView(2, "", "Говядина", Measure.Grams.small),
            IngredientView(3, "", "Свинина", Measure.Grams.small),
            IngredientView(4, "", "Баранина", Measure.Grams.small),
            IngredientView(5, "", "Индейка", Measure.Grams.small),
            IngredientView(6, "", "Куриные грудки", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        1, "Рыба и морепродукты",
        listOf(
            IngredientView(7, "", "Лосось", Measure.Grams.small),
            IngredientView(8, "", "Тунец", Measure.Grams.small),
            IngredientView(9, "", "Креветки", Measure.Grams.small),
            IngredientView(10, "", "Треска", Measure.Grams.small),
            IngredientView(11, "", "Скумбрия", Measure.Grams.small),
            IngredientView(12, "", "Форель", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        2, "Овощи",
        listOf(
            IngredientView(13, "", "Картофель", Measure.Grams.small),
            IngredientView(14, "", "Лук", Measure.Grams.small),
            IngredientView(15, "", "Морковь", Measure.Grams.small),
            IngredientView(16, "", "Чеснок", Measure.Grams.small),
            IngredientView(17, "", "Помидоры", Measure.Grams.small),
            IngredientView(17, "", "Огурцы", Measure.Grams.small),
            IngredientView(17, "", "Зелёный лук", Measure.Grams.small),
            IngredientView(17, "", "Укроп", Measure.Grams.small),
            IngredientView(17, "", "Петрушка", Measure.Grams.small),
            IngredientView(18, "", "Брокколи", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        3, "Фрукты",
        listOf(
            IngredientView(19, "", "Яблоки", Measure.Grams.small),
            IngredientView(20, "", "Бананы", Measure.Grams.small),
            IngredientView(21, "", "Лимоны", Measure.Grams.small),
            IngredientView(22, "", "Апельсины", Measure.Grams.small),
            IngredientView(23, "", "Клубника", Measure.Grams.small),
            IngredientView(24, "", "Груши", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        4, "Зелень и травы",
        listOf(
            IngredientView(25, "", "Петрушка", Measure.Grams.small),
            IngredientView(26, "", "Укроп", Measure.Grams.small),
            IngredientView(27, "", "Базилик", Measure.Grams.small),
            IngredientView(28, "", "Тимьян", Measure.Grams.small),
            IngredientView(29, "", "Орегано", Measure.Grams.small),
            IngredientView(30, "", "Мята", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        5, "Молочные продукты",
        listOf(
            IngredientView(31, "", "Молоко", Measure.Milliliters.small),
            IngredientView(32, "", "Сливочное масло", Measure.Grams.small),
            IngredientView(33, "", "Творог", Measure.Grams.small),
            IngredientView(34, "", "Сыр", Measure.Grams.small),
            IngredientView(35, "", "Сливки", Measure.Milliliters.small),
            IngredientView(36, "", "Сметана", Measure.Milliliters.small),
        ),
    ),
    IngredientCategoryView(
        6, "Зерновые и крупы",
        listOf(
            IngredientView(37, "", "Рис", Measure.Grams.small),
            IngredientView(38, "", "Макароны", Measure.Grams.small),
            IngredientView(39, "", "Овсянка", Measure.Grams.small),
            IngredientView(40, "", "Киноа", Measure.Grams.small),
            IngredientView(41, "", "Гречка", Measure.Grams.small),
            IngredientView(42, "", "Булгур", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        7, "Бобовые",
        listOf(
            IngredientView(43, "", "Фасоль", Measure.Grams.small),
            IngredientView(44, "", "Горох", Measure.Grams.small),
            IngredientView(45, "", "Чечевица", Measure.Grams.small),
            IngredientView(46, "", "Нут", Measure.Grams.small),
            IngredientView(47, "", "Соевые бобы", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        8, "Орехи и семена",
        listOf(
            IngredientView(48, "", "Миндаль", Measure.Grams.small),
            IngredientView(49, "", "Грецкие орехи", Measure.Grams.small),
            IngredientView(50, "", "Фундук", Measure.Grams.small),
            IngredientView(51, "", "Семена подсолнечника", Measure.Grams.small),
            IngredientView(52, "", "Кедровые орешки", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        9, "Специи и приправы",
        listOf(
            IngredientView(53, "", "Соль", Measure.Grams.small),
            IngredientView(54, "", "Черный перец", Measure.Grams.small),
            IngredientView(55, "", "Паприка", Measure.Grams.small),
            IngredientView(56, "", "Корица", Measure.Grams.small),
            IngredientView(57, "", "Имбирь", Measure.Grams.small),
            IngredientView(58, "", "Куркума", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        10, "Соусы и масла",
        listOf(
            IngredientView(59, "", "Оливковое масло", Measure.Milliliters.small),
            IngredientView(60, "", "Соевый соус", Measure.Milliliters.small),
            IngredientView(61, "", "Кетчуп", Measure.Milliliters.small),
            IngredientView(62, "", "Майонез", Measure.Milliliters.small),
            IngredientView(63, "", "Горчица", Measure.Milliliters.small),
        ),
    ),
    IngredientCategoryView(
        11, "Выпечка и десерты",
        listOf(
            IngredientView(64, "", "Мука", Measure.Grams.small),
            IngredientView(65, "", "Сахар", Measure.Grams.small),
            IngredientView(66, "", "Дрожжи", Measure.Grams.small),
            IngredientView(67, "", "Яйца", Measure.Grams.small),
            IngredientView(68, "", "Мёд", Measure.Grams.small),
            IngredientView(69, "", "Хлеб", Measure.Grams.small),
        ),
    ),
    IngredientCategoryView(
        12, "Алкоголь",
        listOf(
            IngredientView(70, "", "Абсент", Measure.Milliliters.small),
            IngredientView(71, "", "Арманьяк", Measure.Milliliters.small),
            IngredientView(72, "", "Вино (сухое белое)", Measure.Milliliters.small),
            IngredientView(73, "", "Водка", Measure.Milliliters.small),
            IngredientView(74, "", "Коньяк", Measure.Milliliters.small),
            IngredientView(75, "", "Виски", Measure.Milliliters.small),
            IngredientView(76, "", "Граппа", Measure.Milliliters.small),
            IngredientView(77, "", "Джин", Measure.Milliliters.small),
            IngredientView(78, "", "Бренди", Measure.Milliliters.small),
            IngredientView(79, "", "Бурбон", Measure.Milliliters.small),
            IngredientView(80, "", "Вермут", Measure.Milliliters.small),
        )
    ),
    IngredientCategoryView(
        13, "Прочее",
        listOf(
            IngredientView(81, "", "Вода", Measure.Milliliters.small),
            IngredientView(82, "", "Бульон", Measure.Milliliters.small),
        )
    ),
)