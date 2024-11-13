package week.on.a.plate.core.utils

import week.on.a.plate.data.dataView.recipe.RecipeView

fun RecipeView.getAllTimeCook():Int{
    val recipe = this
    return if (recipe.steps.isEmpty()){
        0
    }else{
        recipe.steps.maxOf { it.start+it.duration }.toInt()
    }
}