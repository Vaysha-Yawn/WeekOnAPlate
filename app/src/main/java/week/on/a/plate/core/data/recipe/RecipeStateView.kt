package week.on.a.plate.core.data.recipe

enum class RecipeStateView(val names:String, val nextStep:String) {
    Created("Добавлено", "Добавить в список продуктов"),
    InShoppingList("В списке продуктов", "Приготовить"),
    Done("Готово", "Съесть"),
    Eated("Съедено", ""),
}