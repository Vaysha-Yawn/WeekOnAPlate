package week.on.a.plate.data.dataView.example

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val recipeExampleBase = listOf(
    RecipeView(
        id = 0,
        name = "Борщ на курином бульоне",
        description = "Ароматный и сытный борщ, приготовленный на курином бульоне, идеально подходит для обеда.",
        img = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123024.jpg",
        tags = listOf(
            RecipeTagView(0, tagName = "Суп"),
            RecipeTagView(1, tagName = "Курица"),
            RecipeTagView(2, tagName = "Русская кухня")
        ),
        prepTime = 25,
        allTime = 60,
        standardPortionsCount = 4,
        ingredients = listOf(
            IngredientInRecipeView(id = 0, ingredientView = IngredientView(ingredientId = 0, img = "https://example.com/chicken.jpg", name = "Курица", measure = "г"), description = "Любые части курицы", count = 600),
            IngredientInRecipeView(id = 1, ingredientView = IngredientView(ingredientId = 1, img = "https://example.com/cabbage.jpg", name = "Капуста", measure = "г"), description = "Нашинкованная", count = 300),
            IngredientInRecipeView(id = 2, ingredientView = IngredientView(ingredientId = 2, img = "https://example.com/beet.jpg", name = "Свекла", measure = "шт"), description = "Натертая", count = 1),
            IngredientInRecipeView(id = 3, ingredientView = IngredientView(ingredientId = 3, img = "https://example.com/potato.jpg", name = "Картофель", measure = "шт"), description = "Нарезанный", count = 3),
            IngredientInRecipeView(id = 4, ingredientView = IngredientView(ingredientId = 4, img = "https://example.com/carrot.jpg", name = "Морковь", measure = "шт"), description = "Натертая", count = 1),
            IngredientInRecipeView(id = 5, ingredientView = IngredientView(ingredientId = 5, img = "https://example.com/onion.jpg", name = "Лук репчатый", measure = "шт"), description = "Мелко нарезанный", count = 1),
            IngredientInRecipeView(id = 6, ingredientView = IngredientView(ingredientId = 6, img = "https://example.com/tomato_paste.jpg", name = "Томатная паста", measure = "ст. ложка"), description = "Для заправки", count = 2),
            IngredientInRecipeView(id = 7, ingredientView = IngredientView(ingredientId = 7, img = "https://example.com/garlic.jpg", name = "Чеснок", measure = "зубчик"), description = "По желанию", count = 2),
            IngredientInRecipeView(id = 8, ingredientView = IngredientView(ingredientId = 8, img = "https://example.com/vegetable_oil.jpg", name = "Масло подсолнечное", measure = "ст. ложка"), description = "Для обжаривания", count = 2),
            IngredientInRecipeView(id = 9, ingredientView = IngredientView(ingredientId = 9, img = "https://example.com/laurel_leaf.jpg", name = "Лавровый лист", measure = "шт"), description = "Для аромата", count = 2)
        ),
        steps = listOf(
            RecipeStepView(id = 0, description = "Поставьте вариться курицу в холодной воде, доведите до кипения, снимите пену, посолите и варите 30 минут.", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123025.jpg", timer = 30, LocalTime.of(0, 0)),
            RecipeStepView(id = 1, description = "Натрите свеклу и морковь, мелко нарежьте лук.", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123026.jpg", timer = 0, LocalTime.of(0, 0)),
            RecipeStepView(id = 1, description = "Лук обжарить на подсолнечном масле до мягкости.", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123028.jpg", timer = 0, LocalTime.of(0, 0)),
            RecipeStepView(id = 1, description = "Затем добавить морковь, обжарить минуты 3.\n" + "Добавить свеклу и влить немного бульона из кастрюли (можно добавить 1 ч. ложку сахара). Тушить овощи под крышкой до тех пор, пока они не станут мягкими (около 15 минут). В конце добавить томатную пасту или кетчуп, перемешать и потушить еще 3 минуты.", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123032.jpg", timer = 3, LocalTime.of(0, 0)),
            RecipeStepView(id = 2, description = "Добавьте немного бульона и тушите овощи под крышкой 15 минут. Затем добавьте томатную пасту и тушите еще 3 минуты.", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123027.jpg", timer = 15, LocalTime.of(0, 0)),
            RecipeStepView(id = 3, description = "Нашинковать капусту.", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123031.jpg", timer = 0, LocalTime.of(0, 0)
            ),
            RecipeStepView(id = 4, description = "\n" +
                        "Когда курица будет готова, вынуть ее из бульона, отделить мясо от кости.\n" +
                        "В бульон выложить картофель, нарезанный брусками либо кубиками. После закипания добавить капусту и куриное мясо. Варить борщ минут 15, до готовности картофеля и капусты. (Молодую капусту добавлять позже, минут за 5 до окончания приготовления борща.)", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123033.jpg", timer = 15, LocalTime.of(0, 0)),
            RecipeStepView(id = 5, description = "Добавить овощную заправку и лавровый лист. Варить борщ на малом огне еще 4-5 минут. Посолить, поперчить. При желании добавить в борщ выдавленный через пресс или мелко нарезанный чеснок.", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123034.jpg", timer = 5, LocalTime.of(0, 0)),
            RecipeStepView(id = 5, description = "Готовому борщу дать настояться минут 15 и разлить по тарелкам. Подавать борщ со сметаной.\n" +
                        "Приятного аппетита!", image = "https://img1.russianfood.com/dycontent/images_upl/124/sm_123035.jpg", timer = 15, LocalTime.of(0, 0))
        ),
        link = "https://www.russianfood.com/recipes/recipe.php?rid=134380",
        inFavorite = false, LocalDateTime.now(),
    ),

    RecipeView(
        0,
        name = "Селедка под шубой",
        description = "Великий советский салат с крепким союзом сладкой свеклы и соленой рыбы. Этот салат готовится по-разному, но основа остается неизменной. Яблоко добавляет свежесть и смягчает вкус селедки.",
        img = "https://example.com/image_of_salat.jpg", // Update with a relevant image link
        tags = listOf(RecipeTagView(0, tagName = "Салат")),
        prepTime = 120,
        allTime = 120,
        standardPortionsCount = 12,
        ingredients = listOf(
            IngredientInRecipeView(0, IngredientView(ingredientId = 1, img = "https://example.com/selyodka.jpg", name = "Сельдь", measure = "шт"), "1", 1),
            IngredientInRecipeView(1, IngredientView(ingredientId = 2, img = "https://example.com/kartofel.jpg", name = "Картофель", measure = "г"), "300", 300),
            IngredientInRecipeView(2, IngredientView(ingredientId = 3, img = "https://example.com/morkov.jpg", name = "Морковь", measure = "г"), "300", 300),
            IngredientInRecipeView(3, IngredientView(ingredientId = 4, img = "https://example.com/svekla.jpg", name = "Свекла", measure = "г"), "500", 500),
            IngredientInRecipeView(4, IngredientView(ingredientId = 5, img = "https://example.com/yajca.jpg", name = "Куриное яйцо", measure = "шт"), "6", 6),
            IngredientInRecipeView(5, IngredientView(ingredientId = 6, img = "https://example.com/luk.jpg", name = "Красный лук", measure = "г"), "100", 100),
            IngredientInRecipeView(6, IngredientView(ingredientId = 7, img = "https://example.com/yabloko.jpg", name = "Яблоко", measure = "г"), "200", 200),
            IngredientInRecipeView(7, IngredientView(ingredientId = 8, img = "https://example.com/mayonez.jpg", name = "Майонез", measure = "г"), "250", 250),
            IngredientInRecipeView(8, IngredientView(ingredientId = 9, img = "https://example.com/sol.jpg", name = "Соль", measure = "по вкусу"), "по вкусу", 0)
        ),
        steps = listOf(
            RecipeStepView(0, "Подготовить все ингредиенты.", "https://example.com/preparation.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(1, "Обернуть картошку, свеклу и морковь фольгой.", "https://example.com/wrapping.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(2, "Выложить овощи на противень и отправить в духовку, разогретую до 200 градусов. Запекать до мягкости: картошке и моркови понадобится около 40 минут, а свекле — час.", "https://example.com/baking.jpg", 40, LocalTime.of(0, 0)),
            RecipeStepView(3, "В кипящую воду положить яйца и варить их 11 минут. Затем охладить.", "https://example.com/boiling_eggs.jpg", 11, LocalTime.of(0, 0)),
            RecipeStepView(4, "Очистить яйца и натереть их на крупной терке.", "https://example.com/grating_eggs.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(5, "Картошку и морковь очистить и нарезать мелким кубиком. Рыбу очистить, разделить на филе и нарезать таким же кубиком.", "https://example.com/chopping.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(6, "Лук и яблоки нарезать чуть мельче.", "https://example.com/chopping_onions_apples.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(7, "Свеклу натереть на крупной терке.", "https://example.com/grating_beetroot.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(8, "Выложить салат на блюдо слоями, промазывая каждый майонезом: картошка, селедка, лук, яблоки, яйца, морковь, свекла.", "https://example.com/layering.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(9, "Завершающий слой свеклы также смазать майонезом. Готовый салат отправить в холодильник на пару-тройку часов, чтобы дать ему пропитаться.", "https://example.com/refrigerating.jpg", 120, LocalTime.of(0, 0)),
            RecipeStepView(10, "Перед подачей украсить салат зеленью.", "https://example.com/serving.jpg", 0, LocalTime.of(0, 0))
        ),
        link = "https://eda.ru/recepty/salaty/seld-pod-shuboy-139942",
        inFavorite = false, LocalDateTime.now(),
    ),
    RecipeView(
        0,
        name = "Сборная солянка",
        description = "Сытный мясной суп, который можно готовить с различными видами мяса и добавками. Чем больше сортов мяса, тем вкуснее получится солянка. Этот рецепт включает шесть видов мяса, создавая гармоничный вкус.",
        img = "https://example.com/image_of_solyanka.jpg", // Update with a relevant image link
        tags = listOf(RecipeTagView(0, tagName = "Суп")),
        prepTime = 180,
        allTime = 180,
        standardPortionsCount = 8,
        ingredients = listOf(
            IngredientInRecipeView(0, IngredientView(ingredientId = 1, img = "https://example.com/meat_on_bone.jpg", name = "Мясо на кости", measure = "г"), "300", 300),
            IngredientInRecipeView(1, IngredientView(ingredientId = 2, img = "https://example.com/sosiki.jpg", name = "Сосиски", measure = "г"), "100", 100),
            IngredientInRecipeView(2, IngredientView(ingredientId = 3, img = "https://example.com/vetchina.jpg", name = "Ветчина", measure = "г"), "100", 100),
            IngredientInRecipeView(3, IngredientView(ingredientId = 4, img = "https://example.com/kolbas.jpg", name = "Охотничьи колбаски", measure = "г"), "100", 100),
            IngredientInRecipeView(4, IngredientView(ingredientId = 5, img = "https://example.com/varennaya_kolbasa.jpg", name = "Вареная колбаса", measure = "г"), "100", 100),
            IngredientInRecipeView(5, IngredientView(ingredientId = 6, img = "https://example.com/ogurtsy.jpg", name = "Соленые огурцы", measure = "г"), "200", 200),
            IngredientInRecipeView(6, IngredientView(ingredientId = 7, img = "https://example.com/maslini.jpg", name = "Маслины", measure = "г"), "100", 100),
            IngredientInRecipeView(7, IngredientView(ingredientId = 8, img = "https://example.com/luk.jpg", name = "Репчатый лук", measure = "шт"), "2", 2),
            IngredientInRecipeView(8, IngredientView(ingredientId = 9, img = "https://example.com/morkov.jpg", name = "Морковь", measure = "шт"), "1", 1),
            IngredientInRecipeView(9, IngredientView(ingredientId = 10, img = "https://example.com/maslo_rastitelnoe.jpg", name = "Растительное масло", measure = "мл"), "30", 30),
            IngredientInRecipeView(10, IngredientView(ingredientId = 11, img = "https://example.com/maslo_slivocnoe.jpg", name = "Сливочное масло", measure = "г"), "20", 20),
            IngredientInRecipeView(11, IngredientView(ingredientId = 12, img = "https://example.com/tomatnaya_pasta.jpg", name = "Томатная паста", measure = "г"), "70", 70),
            IngredientInRecipeView(12, IngredientView(ingredientId = 13, img = "https://example.com/lavrovyy_list.jpg", name = "Лавровый лист", measure = "шт"), "1", 1),
            IngredientInRecipeView(13, IngredientView(ingredientId = 14, img = "https://example.com/perets.jpg", name = "Душистый перец горошком", measure = "шт"), "5", 5),
            IngredientInRecipeView(14, IngredientView(ingredientId = 15, img = "https://example.com/limon.jpg", name = "Лимон", measure = "по вкусу"), "по вкусу", 0),
            IngredientInRecipeView(15, IngredientView(ingredientId = 16, img = "https://example.com/smetana.jpg", name = "Сметана", measure = "по вкусу"), "по вкусу", 0),
            IngredientInRecipeView(16, IngredientView(ingredientId = 17, img = "https://example.com/petrushka.jpg", name = "Петрушка", measure = "г"), "30", 30),
            IngredientInRecipeView(17, IngredientView(ingredientId = 18, img = "https://example.com/sol.jpg", name = "Соль", measure = "по вкусу"), "по вкусу", 0),
            IngredientInRecipeView(18, IngredientView(ingredientId = 19, img = "https://example.com/perets_chernyy.jpg", name = "Молотый черный перец", measure = "по вкусу"), "по вкусу", 0)
        ),
        steps = listOf(
            RecipeStepView(0, "Подготовить необходимые ингредиенты.", "https://example.com/preparation.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(1, "Мясо залить двумя литрами воды и довести до кипения, затем убавить огонь, снять пенку, добавить целую луковицу и морковь, лавровый лист и перец-горошек. Оставить вариться на час-полтора.", "https://example.com/boiling.jpg", 90, LocalTime.of(0, 0)),
            RecipeStepView(2, "Измельчить лук и обжарить его на смеси растительного и сливочного масла до прозрачности.", "https://example.com/frying_onion.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(3, "Добавить томатную пасту и жарить еще пару минут.", "https://example.com/adding_tomato_paste.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(4, "Мясо из бульона и остальные мясные продукты нарезать небольшими кусочками.", "https://example.com/chopping_meat.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(5, "Нарезать кубиками соленые огурцы.", "https://example.com/chopping_pickles.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(6, "Нарезать колечками маслины и измельчить петрушку.", "https://example.com/chopping_olives.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(7, "Бульон процедить, вернуть на плиту и добавить к нему все мясо, луковую зажарку и нарезанные кубиком соленые огурцы. Варить 15–20 минут на слабом огне.", "https://example.com/simmering.jpg", 20, LocalTime.of(0, 0)),
            RecipeStepView(8, "В конце добавить нарезанные маслины и петрушку.", "https://example.com/adding_olives_parsley.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(9, "Снять с огня, накрыть крышкой и дать настояться 5–10 минут. Подавать с ломтиком лимона и сметаной.", "https://example.com/serving.jpg", 10, LocalTime.of(0, 0))
        ),
        link = "https://eda.ru/recepty/supy/sbornaya-solyanka-139663",
        inFavorite = false, LocalDateTime.now(),
    ),
    RecipeView(
        0,
        name = "Настоящий греческий салат",
        description = "Свежий и легкий салат с фетой, который идеально подходит для летнего обеда. Этот салат с простыми ингредиентами приносит гармонию вкуса и аромата.",
        img = "https://example.com/image_of_greek_salad.jpg", // Update with a relevant image link
        tags = listOf(RecipeTagView(0, tagName = "Салат")),
        prepTime = 10,
        allTime = 10,
        standardPortionsCount = 3,
        ingredients = listOf(
            IngredientInRecipeView(0, IngredientView(ingredientId = 1, img = "https://example.com/pomidory.jpg", name = "Помидоры", measure = "шт"), "2", 2),
            IngredientInRecipeView(1, IngredientView(ingredientId = 2, img = "https://example.com/zelenyy_perec.jpg", name = "Зеленый перец", measure = "шт"), "1", 1),
            IngredientInRecipeView(2, IngredientView(ingredientId = 3, img = "https://example.com/maslini.jpg", name = "Маслины", measure = "шт"), "10", 10),
            IngredientInRecipeView(3, IngredientView(ingredientId = 4, img = "https://example.com/ogurec.jpg", name = "Огурцы", measure = "шт"), "2", 2),
            IngredientInRecipeView(4, IngredientView(ingredientId = 5, img = "https://example.com/oregano.jpg", name = "Сушеный орегано", measure = "ч.л."), "1", 1),
            IngredientInRecipeView(5, IngredientView(ingredientId = 6, img = "https://example.com/feta.jpg", name = "Сыр фета", measure = "г"), "300", 300),
            IngredientInRecipeView(6, IngredientView(ingredientId = 7, img = "https://example.com/maslo_olivkovoye.jpg", name = "Оливковое масло", measure = "мл"), "25", 25),
            IngredientInRecipeView(7, IngredientView(ingredientId = 8, img = "https://example.com/sol.jpg", name = "Соль", measure = "по вкусу"), "по вкусу", 0),
            IngredientInRecipeView(8, IngredientView(ingredientId = 9, img = "https://example.com/luk.jpg", name = "Красный лук", measure = "шт"), "1", 1)
        ),
        steps = listOf(
            RecipeStepView(0, "Подготовить необходимые ингредиенты.", "https://example.com/preparation.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(1, "Огурцы тщательно очистить от кожи и нарезать крупными полукруглыми ломтиками. Сложить в глубокий салатник.", "https://example.com/cutting_cucumbers.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(2, "Помидоры нарезать крупными дольками. Добавить в салатник к огурцам и перемешать, подливая оливковое масло.", "https://example.com/cutting_tomatoes.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(3, "Сладкий зеленый перец очистить от семян и нарезать крупными кубиками. Добавить в салатник и перемешать.", "https://example.com/cutting_green_pepper.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(4, "Красный лук нарезать тонкими полукольцами и разделить на лепестки. Переложить в салатник и снова перемешать.", "https://example.com/cutting_red_onion.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(5, "Добавить маслины без косточек в салатник.", "https://example.com/adding_olives.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(6, "Фету нарезать на прямоугольные ломтики толщиной в 1 см. Чтобы сыр не прилипал, нож можно окунуть в теплую воду.", "https://example.com/cutting_feta.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(7, "Разложить салат по тарелкам и сверху положить по 2 ломтика феты. Щедро полить оливковым маслом.", "https://example.com/serving_salad.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(8, "Слегка посолить салат и посыпать сушеным орегано.", "https://example.com/adding_oregano.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(9, "Сверху снова сбрызнуть оливковым маслом и подавать немедленно.", "https://example.com/serving_final.jpg", 0, LocalTime.of(0, 0))
        ),
        link = "https://eda.ru/recepty/salaty/nastojaschij-grecheskij-salat-30893",
        inFavorite = false, LocalDateTime.now(),
    ),
    RecipeView(
        0,
        name = "Перцы фаршированные мясом и рисом",
        description = "Фаршированный перец — это классика советской кухни. Этот рецепт сочетает в себе мясной фарш, рис и ароматные овощи.",
        img = "https://example.com/image_of_stuffed_peppers.jpg", // Update with a relevant image link
        tags = listOf(RecipeTagView(0, tagName = "Основное блюдо")),
        prepTime = 60,
        allTime = 60,
        standardPortionsCount = 5,
        ingredients = listOf(
            IngredientInRecipeView(0, IngredientView(ingredientId = 1, img = "https://example.com/farsh.jpg", name = "Фарш", measure = "кг"), "0.5", 500),
            IngredientInRecipeView(1, IngredientView(ingredientId = 2, img = "https://example.com/sweet_pepper.jpg", name = "Сладкий перец", measure = "шт"), "8", 8),
            IngredientInRecipeView(2, IngredientView(ingredientId = 3, img = "https://example.com/rice.jpg", name = "Рис", measure = "стакан"), "0.5", 125), // 0.5 стакана ~ 125 г
            IngredientInRecipeView(3, IngredientView(ingredientId = 4, img = "https://example.com/carrot.jpg", name = "Морковь", measure = "г"), "200", 200),
            IngredientInRecipeView(4, IngredientView(ingredientId = 5, img = "https://example.com/onion.jpg", name = "Репчатый лук", measure = "головка"), "3", 3),
            IngredientInRecipeView(5, IngredientView(ingredientId = 6, img = "https://example.com/sour_cream.jpg", name = "Сметана", measure = "г"), "100", 100),
            IngredientInRecipeView(6, IngredientView(ingredientId = 7, img = "https://example.com/tomato_paste.jpg", name = "Томатная паста", measure = "ст. л."), "3", 3),
            IngredientInRecipeView(7, IngredientView(ingredientId = 8, img = "https://example.com/salt.jpg", name = "Соль", measure = "по вкусу"), "по вкусу", 0),
            IngredientInRecipeView(8, IngredientView(ingredientId = 9, img = "https://example.com/oil.jpg", name = "Подсолнечное масло", measure = "ст. л."), "2", 2),
            IngredientInRecipeView(9, IngredientView(ingredientId = 10, img = "https://example.com/black_pepper.jpg", name = "Молотый черный перец", measure = "по вкусу"), "по вкусу", 0)
        ),
        steps = listOf(
            RecipeStepView(0, "Лук мелко нарезать, морковь натереть.", "https://example.com/preparation.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(1, "Половину лука и моркови обжарить на сковороде.", "https://example.com/frying_vegetables.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(2, "Рис отварить до полуготовности.", "https://example.com/cooking_rice.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(3, "Смешать рис с фаршем и обжаренной морковью и луком. Посолить и поперчить по вкусу.", "https://example.com/mixing_filling.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(4, "У перца срезать верхушку и очистить от семян.", "https://example.com/preparing_peppers.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(5, "Начинить перец фаршем.", "https://example.com/stuffing_peppers.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(6, "Сметану с томатной пастой смешать, посолить и поперчить.", "https://example.com/making_sauce.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(7, "Полученную массу выложить на дно кастрюли, добавить оставшуюся половину тертой моркови и лука.", "https://example.com/layering_casserole.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(8, "Сверху выложить перцы и залить водой на высоту половины перца.", "https://example.com/adding_peppers.jpg", 0, LocalTime.of(0, 0)),
            RecipeStepView(9, "Тушить под крышкой на среднем огне 40 минут. Подавать горячими.", "https://example.com/serving.jpg", 0, LocalTime.of(0, 0))
        ),
        link = "https://eda.ru/recepty/osnovnye-blyuda/percy-farshirovannye-myasom-i-risom-26665",
        inFavorite = false, LocalDateTime.now(),
    )
)