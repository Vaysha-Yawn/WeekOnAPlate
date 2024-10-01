package week.on.a.plate.screenMenu.view.topBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R

@Composable
fun EditingRow(
    actionDeleteSelected: () -> Unit,
    actionSelectedToShopList: () -> Unit,
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp)
                .clickable { actionDeleteSelected() },
        )
        Image(
            painter = painterResource(id = R.drawable.add_shopping_cart),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp)
                .clickable { actionSelectedToShopList() },
        )
        //todo add generate prepare
    }
}