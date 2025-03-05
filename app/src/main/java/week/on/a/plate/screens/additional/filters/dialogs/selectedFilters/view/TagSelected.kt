package week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
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
        TagBig(
            tag.tagName,
            MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun IngredientSelected(ingredient: IngredientView, remove:()->Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CloseButton { remove() }
        TagBig(
            ingredient.name,
             MaterialTheme.colorScheme.primary
        )
    }
}