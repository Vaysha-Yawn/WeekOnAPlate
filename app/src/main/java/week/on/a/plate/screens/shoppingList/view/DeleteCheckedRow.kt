package week.on.a.plate.screens.shoppingList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.screens.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.shoppingList.state.ShoppingListUIState

@Composable
fun DeleteCheckedRow(
    state: ShoppingListUIState,
    onEvent: (ShoppingListEvent) -> Unit
) {
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
                contentDescription = "Delete", modifier = Modifier
                    .clickNoRipple {
                        onEvent(ShoppingListEvent.DeleteChecked)
                    }
                    .size(36.dp)
            )
        }
    }
}