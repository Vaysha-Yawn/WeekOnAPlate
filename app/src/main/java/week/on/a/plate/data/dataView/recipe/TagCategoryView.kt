package week.on.a.plate.data.dataView.recipe


data class TagCategoryView(
    val id: Long = 0,
    val name: String,
    var tags: List<RecipeTagView>,
)
