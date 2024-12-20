package week.on.a.plate.data.dataView.example


import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.week.Position

val ingredientTomato = IngredientView(0, "https://static.1000.menu/res/380/img/content/36406/salat-iz-pomidorov-s-ogurcom-i-lukom_1562165388_5_max.jpg", "Помидор", "штук")

val positionIngredientExample =
    Position.PositionIngredientView(0, IngredientInRecipeView(0, ingredientTomato, "Целые", 6), 0)
