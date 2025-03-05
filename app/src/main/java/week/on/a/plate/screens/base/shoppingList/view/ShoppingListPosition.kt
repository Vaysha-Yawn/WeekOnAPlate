package week.on.a.plate.screens.base.shoppingList.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorSubTextGrey
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

@Composable
fun ShoppingListPosition(
    item: IngredientInRecipeView,
    checked: Boolean,
    edit: (IngredientInRecipeView) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickNoRipple {
                edit(item)
            }
            .padding(horizontal = 24.dp)
    ) {
        Checkbox(
            checked = checked,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                checkmarkColor = MaterialTheme.colorScheme.onBackground
            ),
            onCheckedChange = {
                onCheckedChange(!checked)
            },
        )
        Spacer(modifier = Modifier.width(12.dp))

        val valueAndMeasure =
            getIngredientCountAndMeasure1000(
                LocalContext.current,
                item.count,
                item.ingredientView.measure
            )
        Column {
            val text = "${item.ingredientView.name} " +
                    valueAndMeasure.first +
                    " ${valueAndMeasure.second}"
            Text(
                text = text,
                textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None,
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.bodyMedium,
                textAlign = TextAlign.Start
            )
            if (item.description != "") {
                Spacer(modifier = Modifier.height(3.dp))
                TextSmall(text = item.description, color = ColorSubTextGrey)
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}