package week.on.a.plate.core.wrapperDatePicker.view.topBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBodyDisActive

@Composable
fun EditingRow(
    actionDeleteSelected: () -> Unit,
    actionSelectedToShopList: () -> Unit,
    actionExit: () -> Unit,
) {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
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

        TextBodyDisActive(
            text = stringResource(R.string.exit_selection_mode),
            modifier = Modifier
                .clickable {
                    actionExit()
                }
                .padding(start = 12.dp)
        )
    }
}