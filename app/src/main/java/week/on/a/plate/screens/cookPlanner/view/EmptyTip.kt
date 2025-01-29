package week.on.a.plate.screens.cookPlanner.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle

@Composable
fun EmptyTip() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        TextTitle(stringResource(R.string.seems_empty))
        Spacer(Modifier.height(24.dp))
        TextBody(stringResource(R.string.cook_planner_empty_tip))
        Spacer(Modifier.height(24.dp))
    }
}