package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.ui.theme.ColorButton
import week.on.a.plate.ui.theme.ColorText
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun EditButton(actionEdit: () -> Unit) {
    Image(painter =  rememberVectorPainter(Icons.Rounded.MoreVert), contentDescription = "", Modifier.background(Color.White).clickable { actionEdit() })
}

@Preview(showBackground = true)
@Composable
fun PreviewEditButton() {
    WeekOnAPlateTheme {
        EditButton() {}
    }
}