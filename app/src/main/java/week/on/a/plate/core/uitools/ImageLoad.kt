package week.on.a.plate.core.uitools

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import week.on.a.plate.R
import week.on.a.plate.screens.searchRecipes.logic.imageFromGallery.getSavedPicture

@Composable
fun ImageLoad(url: String, modifier: Modifier) {
    val img = remember { mutableStateOf(url) }
    img.value = url
    if (url.startsWith("http")) {
        AsyncImage(
            modifier = modifier.clip(RoundedCornerShape(20.dp)),
            model = img.value.lowercase(),
            contentDescription = "",
            placeholder = painterResource(
                id = R.drawable.time
            ),
        )
    } else {
        val context = LocalContext.current
        val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

        LaunchedEffect(img.value) {
            val picture = getSavedPicture(context, img.value)
            imageBitmap.value = picture
        }

        if (imageBitmap.value != null) {
            Image(
                bitmap = imageBitmap.value!!.asImageBitmap(),
                "",
                modifier = modifier.clip(RoundedCornerShape(20.dp))
            )
        }
    }
}