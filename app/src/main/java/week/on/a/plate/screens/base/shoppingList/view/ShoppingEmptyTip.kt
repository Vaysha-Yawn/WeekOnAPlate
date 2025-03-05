package week.on.a.plate.screens.base.shoppingList.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.screens.base.shoppingList.state.ShoppingListUIState

@Composable
fun ShoppingEmptyTip(state: ShoppingListUIState) {
    if (state.listChecked.value.isEmpty() && state.listUnchecked.value.isEmpty()) {
        Column(Modifier.padding(24.dp)) {
            TextTitle(stringResource(R.string.hint_empty))
            Spacer(Modifier.height(12.dp))
            TextBody(stringResource(R.string.hint_add_to_shopping_list))
            Spacer(Modifier.height(24.dp))
        }
    }
}