package week.on.a.plate.core.uitools

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import week.on.a.plate.R

@Composable
fun ImageLoad(url: String, modifier: Modifier) {
    AsyncImage(
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
        model = url.lowercase(),
        contentDescription = null,
        placeholder = painterResource(
            id = R.drawable.time
        ),
    )
}