package week.on.a.plate.screens.base.shoppingList.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.screens.base.shoppingList.event.ShoppingListEvent

@Composable
fun ShoppingTopBar(
    onEvent: (ShoppingListEvent) -> Unit,
    context: Context
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
            Icon(painterResource(R.drawable.delete), "Delete", modifier = Modifier
                .size(48.dp)
                .clickNoRipple {
                    onEvent(ShoppingListEvent.DeleteAll(context))
                }
            )
            Spacer(Modifier.width(12.dp))
            Icon(painterResource(R.drawable.share), "Share", modifier = Modifier
                .size(38.dp)
                .clickNoRipple {
                    onEvent(ShoppingListEvent.Share(context))
                }
            )
        }
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
    Spacer(modifier = Modifier.height(12.dp))
}