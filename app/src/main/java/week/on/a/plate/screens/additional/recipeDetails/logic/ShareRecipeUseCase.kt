package week.on.a.plate.screens.additional.recipeDetails.logic

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.core.ShareUseCase

class ShareRecipeUseCase(context: Context): ShareUseCase(context) {
    private fun getTextShareRecipe(
        recipeView: RecipeView
    ) :String {
        var text = ""
        text += context.getString(R.string.recipe)+ ": " + recipeView.name
        text += "\n"
        text += "\n"
        text +=  context.getString(R.string.cook_time)+ ": " + recipeView.duration.toSecondOfDay().timeToString(context)
        text += "\n"
        text += context.getString(R.string.portions_count)+ ": "+ recipeView.standardPortionsCount
        text += "\n"
        text += "\n"
        text += context.getString(R.string.ingredients)+ ": "
        for (ingredient in recipeView.ingredients) {
            text += "\n"
            val tet = getIngredientCountAndMeasure1000(
                context,
                ingredient.count,
                ingredient.ingredientView.measure
            )
            text += "- " + ingredient.ingredientView.name + " " + ingredient.description + " " + tet.first + " " + tet.second
        }
        text += "\n"
        text += "\n"
        text += context.getString(R.string.cooking)
        for ((index, step) in recipeView.steps.withIndex()) {
            text += "\n"
            text += (index + 1).toString() + ". " + step.description
        }
        text += "\n"
        text += "\n"
        text += context.getString(R.string.source)+ ": " + recipeView.link
        text += "\n"
        text += "\n"
        text += context.getString(R.string.signature)
        return text
    }

    fun shareRecipe(recipeView: RecipeView){
        val text = getTextShareRecipe(recipeView)
        shareAction(text)
    }

}