package week.on.a.plate.core.data.recipe


data class TagCategoryView(
    val id: Long = 0,
    val name: String,
    var tags: List<RecipeTagView>,
)
