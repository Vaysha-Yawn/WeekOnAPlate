package week.on.a.plate.screens.shoppingList.view

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.ads.NativeAdRow
import week.on.a.plate.core.theme.ColorSubTextGrey
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.uitools.TextTitleLargeItalic
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.screens.filters.view.clickNoRipple
import week.on.a.plate.screens.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.shoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screens.shoppingList.state.ShoppingListUIState

@Composable
fun ShoppingListStart(viewModel: ShoppingListViewModel) {
    viewModel.state.listChecked = viewModel.allItemsChecked.collectAsState()
    viewModel.state.listUnchecked = viewModel.allItemsUnChecked.collectAsState()
    ShoppingListContent(viewModel.state) { event: ShoppingListEvent ->
        viewModel.onEvent(event)
    }
}

@Composable
fun ShoppingListContent(state: ShoppingListUIState, onEvent: (ShoppingListEvent) -> Unit) {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextTitleLarge(
                text = stringResource(R.string.shopping_list),
                modifier = Modifier.weight(1f)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(R.drawable.delete), "", modifier = Modifier
                    .clickNoRipple {
                        onEvent(ShoppingListEvent.DeleteAll(context))
                    }
                    .size(30.dp))
                Spacer(Modifier.width(12.dp))
                Icon(painterResource(R.drawable.share), "", modifier = Modifier.clickNoRipple {
                    onEvent(ShoppingListEvent.Share(context))
                })
            }
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn() {
            items(state.listUnchecked.value.size) { index ->
                ShoppingListPosition(state.listUnchecked.value[index], false, {
                    onEvent(ShoppingListEvent.Edit(it))
                }) {
                    onEvent(ShoppingListEvent.Check(state.listUnchecked.value[index]))
                }
            }
            item {
                if (state.listChecked.value.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextBody(text = stringResource(R.string.bought))
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "", modifier = Modifier
                                .clickNoRipple {
                                    onEvent(ShoppingListEvent.DeleteChecked)
                                }
                                .size(36.dp)
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.height(12.dp))
                NativeAdRow("R-M-13419544-6")
                Spacer(Modifier.height(12.dp))
            }
            items(state.listChecked.value.size) { index ->
                ShoppingListPosition(state.listChecked.value[index], true, {
                    onEvent(ShoppingListEvent.Edit(it))
                }) {
                    onEvent(ShoppingListEvent.Uncheck(state.listChecked.value[index]))
                }
            }
        }
        if (state.listChecked.value.isEmpty() && state.listUnchecked.value.isEmpty()){
            Column(Modifier.padding(24.dp)) {
                TextTitle(stringResource(R.string.hint_empty))
                Spacer(Modifier.height(12.dp))
                TextBody(stringResource(R.string.hint_add_to_shopping_list))
                Spacer(Modifier.height(24.dp))
                NativeAdRow("R-M-13419544-6")
            }
        }
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
            getIngredientCountAndMeasure1000(LocalContext.current, item.count, item.ingredientView.measure)
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

@Preview(showBackground = true)
@Composable
fun PreviewShoppingListStart() {
    WeekOnAPlateTheme {
        ShoppingListContent(ShoppingListUIState(), {})
    }
}