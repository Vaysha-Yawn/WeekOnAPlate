package week.on.a.plate.screenShoppingList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorSubTextGrey
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.uitools.buttons.ActionPlusButton
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.screenShoppingList.event.ShoppingListEvent
import week.on.a.plate.screenShoppingList.logic.ShoppingListViewModel

@Composable
fun ShoppingListStart(viewModel: ShoppingListViewModel) {

    viewModel.state.listChecked = viewModel.allItemsChecked.collectAsState()
    viewModel.state.listUnchecked = viewModel.allItemsUnChecked.collectAsState()

    Scaffold(floatingActionButton = {
        ActionPlusButton {
            viewModel.onEvent(ShoppingListEvent.Add)
        }
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TextTitleLarge(
                text = "Список покупок",
                modifier = Modifier.padding(horizontal = 36.dp, vertical = 12.dp)
            )
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn() {
                items(viewModel.state.listUnchecked.value.size) { index ->
                    ShoppingListPosition(viewModel.state.listUnchecked.value[index], false, {
                        viewModel.onEvent(ShoppingListEvent.Edit(it))
                    }) {
                        viewModel.onEvent(ShoppingListEvent.Check(viewModel.state.listUnchecked.value[index]))
                    }
                }
                item {
                    if (viewModel.state.listChecked.value.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp, vertical = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextBody(text = "Куплено")
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = "", modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(ShoppingListEvent.DeleteChecked)
                                    }
                                    .size(36.dp)
                            )
                        }
                    }
                }
                items(viewModel.state.listChecked.value.size) { index ->
                    ShoppingListPosition(viewModel.state.listChecked.value[index], true, {
                        viewModel.onEvent(ShoppingListEvent.Edit(it))
                    }) {
                        viewModel.onEvent(ShoppingListEvent.Uncheck(viewModel.state.listChecked.value[index]))
                    }
                }
            }
        }
        innerPadding
    }
}

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
            .clickable {
                edit(item)
            }
            .padding(horizontal = 24.dp)
    ) {
        Checkbox(
            checked = checked,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.secondary),
            onCheckedChange = {
                onCheckedChange(!checked)
            },
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            val text = "${item.ingredientView.name} " +
                    "${item.count}" +
                    " ${item.ingredientView.measure}"
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