package week.on.a.plate.core.data.example

import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeStepView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.core.data.week.RecipeShortView

val recipeTom =
    RecipeView(
        0,
        "Салат из помидоров с огурцом и луком",
        description = "",
        img = "https://static.1000.menu/res/640/img/content-v2/dc/1d/36406/salat-iz-pomidorov-s-ogurcom-i-lukom_1613988871_7_max.jpg",
        tags = listOf(RecipeTagView(0, "салат"), RecipeTagView(0, "салат")),
        prepTime = 15,
        allTime = 15,
        6,
        ingredients = listOf(
            IngredientInRecipeView(
                0,
                IngredientView(
                    ingredientId = 0,
                    img = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.picserver.org%2Fpictures%2Ftomato04-lg.jpg&f=1&nofb=1&ipt=05d4833f4f489ccf40ab3b5648854de472c3b3eac7a8a7b766696246a60e61a0&ipo=images",
                    name = "Томаты",
                    measure = "гр"
                ), "", 300.0
            ),
            IngredientInRecipeView(
                0,
                IngredientView(
                    ingredientId = 1,
                    img = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fseloveselo.online%2Fwp-content%2Fuploads%2F2019%2F10%2F34626.jpg&f=1&nofb=1&ipt=208111b41783161fb588a1d940eecded4cf825b570906a59c4531a2cbd3d9668&ipo=images",
                    name = "Огурцы",
                    measure = "гр"
                ), "", 300.0
            ),
            IngredientInRecipeView(
                0,
                IngredientView(
                    ingredientId = 2,
                    img = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.rabstol.net%2Fuploads%2Fgallery%2Fmain%2F549%2Frabstol_net_vegetables_19.jpg&f=1&nofb=1&ipt=18c2a467ef663272adc050d953a3db35bcedc97761650714f29fb04f1bf48e9f&ipo=images",
                    name = "Лук репчатый",
                    measure = "гр"
                ), "", 120.0
            ),
            IngredientInRecipeView(
                0,
                IngredientView(
                    ingredientId = 3,
                    img = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fstatic8.pardokht.ru%2Fimages%2F282d736f-a15f-488d-a99d-e6584d12d9ea..jpg&f=1&nofb=1&ipt=1e419bd127198c42d2b1e0593ffe2d5e45598dae2070220c0b948b8f5d6ad8e3&ipo=images",
                    name = "Укроп",
                    measure = "гр"
                ), "", 15.0
            ),
            IngredientInRecipeView(
                0,
                IngredientView(
                    ingredientId = 4,
                    img = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftakprosto.cc%2Fwp-content%2Fuploads%2Fk%2Fkak-vybrat-rastitelnoe-maslo%2F1.jpg&f=1&nofb=1&ipt=2b2caeaa2646e6e27d987d9f4c7370d0e407d8c7270a2e4d04277f9604fa210f&ipo=images",
                    name = "Растительное масло",
                    measure = "стол.л."
                ), "", 3.0
            ),
            IngredientInRecipeView(
                0,
                IngredientView(
                    ingredientId = 5,
                    img = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Ffood-tips.ru%2Fwp-content%2Fuploads%2F2017%2F12%2FExternalLink_shutterstock_277948187.jpg&f=1&nofb=1&ipt=610a59928138caae9bb2f2ac500ad48189607488417b710688eefbde0c73b8fb&ipo=images",
                    name = "Соль",
                    measure = "стол.л."
                ), "", 0.2
            ),
        ),
        steps = listOf(
            RecipeStepView(
                0,
                "",
                "Как сделать салат из свежих огурцов и помидоров? Подготовьте продукты. Овощи и зелень хорошо вымойте и обсушите салфетками. Масло можете брать любое растительное.",
                "https://static.1000.menu/res/380/img/content/36406/salat-iz-pomidorov-s-ogurcom-i-lukom_1562163876_1_max.jpg",
                0
            ),
            RecipeStepView(
                1,
                "",
                "Если шкурка у огурцов горчит, или слишком жесткая, или вы просто хотите, чтобы готовый салат был более мягким, то срежьте ее. Также срежьте и кончики у огурцов. Сами огурцы нарежьте ломтиками небольшого размера и переложите в миску для салата.",
                "https://static.1000.menu/res/380/img/content/36406/salat-iz-pomidorov-s-ogurcom-i-lukom_1562163876_2_max.jpg",
                0
            ),
            RecipeStepView(
                2,
                "",
                "Помидоры нарежьте ломтиками примерно такого же размера и формы, как огурцы. Основания плодоножек удаляйте. Добавьте измельченные помидоры в миску к огурцам.",
                "https://static.1000.menu/res/380/img/content/36406/salat-iz-pomidorov-s-ogurcom-i-lukom_1562163876_3_max.jpg",
                0
            ),
            RecipeStepView(
                3,
                "",
                "Лук почистите и нарежьте тонкими полукольцами или четверть кольцами. Добавьте к измельченным овощам, разделив отдельные ломтики лука руками. Остается только заправить салат из помидоров с огурцом и луком растительным маслом, посолить по вкусу и добавить молотый черный перец на свое усмотрение. Не забудьте мелко нарезать и добавить свежий укроп.",
                "https://static.1000.menu/res/380/img/content/36406/salat-iz-pomidorov-s-ogurcom-i-lukom_1562163876_4_max.jpg",
                0
            ),
            RecipeStepView(
                4,
                "",
                "Перемешайте все подготовленные ингредиенты и подавайте салат из свежих огурцов и помидоров с луком сразу же после приготовления. Он вкусен именно свежим.",
                "https://static.1000.menu/res/380/img/content/36406/salat-iz-pomidorov-s-ogurcom-i-lukom_1562165388_5_max.jpg",
                0
            ),
        ),
        link = "https://1000.menu/cooking/36406-salat-iz-pomidorov-s-ogurcom-i-lukom"
    )


val shortRecipe = RecipeShortView(0, "Салат из помидоров с огурцом и луком")


val emptyRecipe = RecipeView(
    0, "name", "description", "img", listOf<RecipeTagView>(),
    0, 0, 0, listOf(), listOf(), ""
)

fun getSimpleRecipe(names: String, inFavorite:Boolean = false, image:String=""): RecipeView {
    return RecipeView(
        0, names, "description", image, if(inFavorite) {
            listOf<RecipeTagView>(
                RecipeTagView(0, "Избранное")
            )
        }else listOf(),
        0, 0, 0, listOf(), listOf(), ""
    )
}

val recipes = listOf(
    recipeTom,
    getSimpleRecipe("Фалафель"),
    getSimpleRecipe("Пучеро", true),
    getSimpleRecipe("Корн-дог"),
    getSimpleRecipe("Сдобные булочки"),
    getSimpleRecipe("Тонкацу", image = "https://eda.ru/images/RecipeStep/434x295/tonkacu_151131_step_11.webp"),
    getSimpleRecipe("Бифштекс"),
    getSimpleRecipe("Винартерта", true),
    getSimpleRecipe("Торт Сантьго"),
    getSimpleRecipe("Миллионбеф", true, image = "https://eda.ru/images/RecipeStep/434x295/millionbef_151073_step_10.webp"),
    getSimpleRecipe("Гречанки", image = "https://eda.ru/images/RecipeStep/434x295/grechaniki_150986_step_10.webp"),
    getSimpleRecipe("Картофельный салат", image = "https://eda.ru/images/RecipeStep/434x295/kartofelnyy-salat_150955_step_10.webp"),
    getSimpleRecipe("Кекс с изюмом", image = "https://eda.ru/images/RecipeStep/434x295/keks-s-izyumom_140792_step_11.webp"),
    getSimpleRecipe("Панеттоне"),
    getSimpleRecipe("Пахлава"),
)