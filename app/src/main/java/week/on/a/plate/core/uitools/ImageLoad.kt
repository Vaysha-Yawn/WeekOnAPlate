package week.on.a.plate.core.uitools

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import week.on.a.plate.R

@Composable
fun ImageLoad(url: String, modifier: Modifier) {
    AsyncImage(
        modifier = modifier,
        model = url,
        contentDescription = null,
        placeholder = painterResource(
            id = R.drawable.time
        )
    )
}