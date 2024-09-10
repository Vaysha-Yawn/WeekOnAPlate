package week.on.a.plate.menuScreen.view.uiTools

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.ui.theme.ColorBluePanel
import week.on.a.plate.ui.theme.ColorTransparent

@Composable
fun EditingRow(
    actionChooseAll: () -> Unit,
    actionDeleteSelected: () -> Unit,
    actionSelectedToShopList: () -> Unit,
    value: Boolean,
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.select_all),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp)
                .background(if (value) ColorBluePanel else ColorTransparent, CircleShape)
                .clickable { actionChooseAll() },
        )
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
    }
}