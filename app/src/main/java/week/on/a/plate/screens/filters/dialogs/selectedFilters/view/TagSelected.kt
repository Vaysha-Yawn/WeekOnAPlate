package week.on.a.plate.screens.filters.dialogs.selectedFilters.view

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.core.uitools.TagBig
import week.on.a.plate.core.uitools.buttons.CloseButton

@Composable
fun TagSelected(tag: RecipeTagView, remove:()->Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CloseButton { remove() }
        TagBig(tag = tag, true){
            remove()
        }
    }
}

@Composable
fun IngredientSelected(ingredient: IngredientView, remove:()->Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CloseButton { remove() }
        TagBig(ingredientView = ingredient, true){
            remove()
        }
    }
}