package week.on.a.plate.screens.base.shoppingList.view

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.BuildCompat
import week.on.a.plate.BuildConfig
import week.on.a.plate.core.ads.NativeAdRow
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.base.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.base.shoppingList.logic.ShoppingListViewModel
import week.on.a.plate.screens.base.shoppingList.state.ShoppingListUIState

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
        ShoppingTopBar(onEvent, context)
        LazyColumn() {
            items(state.listUnchecked.value.size) { index ->
                ShoppingListPosition(state.listUnchecked.value[index], false, {
                    onEvent(ShoppingListEvent.Edit(it))
                }) {
                    onEvent(ShoppingListEvent.Check(state.listUnchecked.value[index]))
                }
            }
            item {
                DeleteCheckedRow(state, onEvent)
            }
            item {
                Spacer(Modifier.height(12.dp))
                if (state.listChecked.value.isNotEmpty() || state.listUnchecked.value.isNotEmpty()){
                    NativeAdRow(BuildConfig.shoppingAdsId)
                    Spacer(Modifier.height(12.dp))
                }
            }
            items(state.listChecked.value.size) { index ->
                ShoppingListPosition(state.listChecked.value[index], true, {
                    onEvent(ShoppingListEvent.Edit(it))
                }) {
                    onEvent(ShoppingListEvent.Uncheck(state.listChecked.value[index]))
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