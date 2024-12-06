package week.on.a.plate.core.uitools

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import week.on.a.plate.R
import week.on.a.plate.mainActivity.logic.imageFromGallery.getSavedPicture

@Composable
fun ImageLoad(url: String, modifier: Modifier) {
    val imageContainer = remember { mutableStateOf<ImageBitmap?>(null) }
    if (url.startsWith("http")) {
        AsyncImage(
            modifier = modifier.clip(RoundedCornerShape(20.dp)),
            model = url.lowercase(),
            contentDescription = "",
            placeholder = painterResource(
                id = R.drawable.time
            ),
        )
    } else {
        val context = LocalContext.current

        LaunchedEffect(url) {
            val picture = getSavedPicture(context, url)
            imageContainer.value = picture?.asImageBitmap()
        }

        if (imageContainer.value != null) {
            Image(
                bitmap = imageContainer.value!!,
                "",
                modifier = modifier.clip(RoundedCornerShape(20.dp))
            )
        }
    }
}

@Composable
fun ImageLoadEditable(url: String, imageContainer: MutableState<ImageBitmap?>, modifier: Modifier) {
    if (url.startsWith("http")) {
        AsyncImage(
            modifier = modifier.clip(RoundedCornerShape(20.dp)),
            model = url.lowercase(),
            contentDescription = "",
            placeholder = painterResource(
                id = R.drawable.time
            ),
        )
    } else {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            if (imageContainer.value == null && url!=""){
                val picture = getSavedPicture(context, url)
                imageContainer.value = picture?.asImageBitmap()
            }
        }

        if (imageContainer.value != null) {
            Image(
                bitmap = imageContainer.value!!,
                "",
                modifier = modifier.clip(RoundedCornerShape(20.dp))
            )
        }
    }
}