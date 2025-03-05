package week.on.a.plate.screens.additional.filters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.clickNoRipple

@Composable
fun FilterItemWithMore(
    text: String,
    isActive: Boolean,
    isIngredient: Boolean,
    click: () -> Unit,
    more: () -> Unit
) {
    Row(
        Modifier
            .background(
                if (isActive) {
                    if (isIngredient) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                } else MaterialTheme.colorScheme.background,
                RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        TextBody(
            text, modifier = Modifier
                .defaultMinSize(minWidth = 48.dp)
                .clickNoRipple(click)
        )
        Spacer(Modifier.width(6.dp))
        Icon(
            painterResource(R.drawable.more), "More", tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickNoRipple(more)
        )
    }
}