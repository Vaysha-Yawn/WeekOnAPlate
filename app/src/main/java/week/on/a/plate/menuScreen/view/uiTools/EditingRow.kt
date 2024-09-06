package week.on.a.plate.menuScreen.view.uiTools

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextInAppColored
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey
import week.on.a.plate.ui.theme.Typography

@Composable
fun EditingRow(
    actionChooseAll:()->Unit,
    actionDeleteSelected:()->Unit,
    actionSelectedToShopList:()->Unit,
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        TextInAppColored(
            "Выбрать всё", colorBackground = ColorButtonNegativeGrey, modifier = Modifier
                .clickable (onClick = actionChooseAll),
            textStyle = Typography.bodyMedium
        )
        TextInAppColored(
            "Удалить", colorBackground = ColorButtonNegativeGrey, modifier = Modifier
                .clickable (onClick = actionDeleteSelected),
            textStyle = Typography.bodyMedium
        )
        TextInAppColored(
            "В список покупок ", colorBackground = ColorButtonNegativeGrey, modifier = Modifier
                .clickable (onClick = actionSelectedToShopList),
            textStyle = Typography.bodyMedium
        )
    }
    Spacer(Modifier.height(10.dp))
    HorizontalDivider(thickness = 1.dp)
}