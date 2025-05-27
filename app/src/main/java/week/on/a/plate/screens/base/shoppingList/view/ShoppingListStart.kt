package week.on.a.plate.screens.base.shoppingList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.base.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.base.shoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screens.base.shoppingList.state.ShoppingListUIState

@Composable
fun ShoppingListStart(viewModel: ShoppingListViewModel = viewModel()) {
    viewModel.state.listChecked = viewModel.allItemsChecked.collectAsState()
    viewModel.state.listUnchecked = viewModel.allItemsUnChecked.collectAsState()
    ShoppingListContent(viewModel.state) { event: ShoppingListEvent ->
        viewModel.onEvent(event)
    }
    MainEventResolve(viewModel.mainEvent, viewModel.dialogOpenParams)
}

@Composable
private fun ShoppingListContent(state: ShoppingListUIState, onEvent: (ShoppingListEvent) -> Unit) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ShoppingTopBar(onEvent, context)
        LazyColumn() {
            items(items = state.listUnchecked.value, key = { it.id }) { ingredient ->
                ShoppingListPosition(ingredient, false, {
                    onEvent(ShoppingListEvent.Edit(it))
                }) {
                    onEvent(ShoppingListEvent.Check(ingredient))
                }
            }
            item {
                DeleteCheckedRow(state, onEvent)
                Spacer(Modifier.height(12.dp))
            }
            items(items = state.listChecked.value, key = { it.id }) { ingredient ->
                ShoppingListPosition(ingredient, true, {
                    onEvent(ShoppingListEvent.Edit(it))
                }) {
                    onEvent(ShoppingListEvent.Uncheck(ingredient))
                }
            }
        }
        ShoppingEmptyTip(state)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShoppingListStart() {
    WeekOnAPlateTheme {
        ShoppingListContent(ShoppingListUIState(), {})
    }
}