package week.on.a.plate.core.data.recipe

data class RecipeView(
    val id: Long,
    var name: String,
    var img:String,
    var tags: List<RecipeTag>,
    var prepTime: Int,
    var allTime: Int,
    var standardPortionsCount: Int,
    var ingredients: List<IngredientInRecipe>,
    var steps: List<RecipeStep>,
    var link: String
)