package week.on.a.plate.core.data.recipe


data class TagCategory(
    val id: Long = 0,
    val name: String,
    var tags: List<RecipeTag>,
)
