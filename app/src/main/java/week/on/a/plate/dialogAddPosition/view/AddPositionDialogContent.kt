package week.on.a.plate.dialogAddPosition.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.dialogAddPosition.event.AddPositionEvent
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun AddPositionDialogContent(onEvent: (AddPositionEvent) -> Unit) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface).padding(20.dp)) {
        ButtonRowPosition(
            R.drawable.add_recipe,
            stringResource(R.string.add_recipe),
        ) {
            onEvent(AddPositionEvent.AddRecipe)
        }

        ButtonRowPosition(
            R.drawable.add_food,
            stringResource(R.string.add_ingredient),
        ) {
            onEvent(AddPositionEvent.AddIngredient)
        }

        ButtonRowPosition(
            R.drawable.add_draft,
            stringResource(R.string.add_draft),
        ) {
            onEvent(AddPositionEvent.AddDraft)
        }

        ButtonRowPosition(
            R.drawable.add_note,
            stringResource(R.string.add_note),
        ) {
            onEvent(AddPositionEvent.AddNote)
        }
    }
}

@Composable
fun ButtonRowPosition(imgRec: Int, text: String, event: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable { event() }, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imgRec),
            contentDescription = text,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(36.dp)
        )
        TextBody(text = text)
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
}

@Preview(showBackground = true)
@Composable
fun PreviewAddPosition() {
    WeekOnAPlateTheme {
        AddPositionDialogContent() {}
    }
}