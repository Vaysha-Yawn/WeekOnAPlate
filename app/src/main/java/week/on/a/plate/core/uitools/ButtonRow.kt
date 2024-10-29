package week.on.a.plate.core.uitools

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ButtonRow(imgRec: Int, imgSecRec: Int? = null, text: String, event: () -> Unit) {
    Row(modifier = Modifier
        .padding(vertical = 10.dp)
        .clickable { event() }) {
        Image(
            painter = painterResource(id = imgRec),
            contentDescription = text,
            modifier = Modifier
                .size(24.dp)
        )
        if (imgSecRec != null) {
            Image(
                painter = painterResource(id = imgSecRec),
                contentDescription = text,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Spacer(Modifier.size(12.dp))
        TextBody(text = text)
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
}