package week.on.a.plate.screens.shoppingList.logic

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.core.ShareUseCase
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

class ShareShoppingListUseCase(context: Context): ShareUseCase(context) {

    private fun getTextShareShoppingList(
        listToShop: List<IngredientInRecipeView>
    ):String {
        var text = ""
        text += context.getString(R.string.share_text_title)
        for (item in listToShop) {
            text += "\n"
            val tet =
                getIngredientCountAndMeasure1000(context, item.count, item.ingredientView.measure)
            text += "- " + item.ingredientView.name + " " + item.description + " " + tet.first + " " + tet.second
        }
        text += "\n"
        text += "\n"
        text += context.getString(R.string.signature)
        return text
    }



    fun shareShoppingList( listToShop: List<IngredientInRecipeView>){
        val text = getTextShareShoppingList(listToShop)
        shareAction(text)
    }
}