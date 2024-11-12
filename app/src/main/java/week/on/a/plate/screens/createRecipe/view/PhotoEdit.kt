package week.on.a.plate.screens.createRecipe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

@Composable
fun PhotoRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    if (state.photoLink.value == "") {
        Row(
            Modifier
                .clickable {
                    onEvent(RecipeCreateEvent.EditMainImage)
                }
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.photo), contentDescription = "")
            Spacer(modifier = Modifier.width(5.dp))
            TextBody(text = "Добавить фото")
        }
        Spacer(modifier = Modifier.width(24.dp))
    }
    if (state.photoLink.value != "") {
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "",
                modifier = Modifier.clickable {
                    state.photoLink.value = ""
                })
            Spacer(modifier = Modifier.width(12.dp))
            ImageLoad(url = state.photoLink.value, modifier = Modifier
                .height(160.dp)
                .clickable {
                    onEvent(RecipeCreateEvent.EditMainImage)
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoRecipeEdit() {
    WeekOnAPlateTheme {
        PhotoRecipeEdit(RecipeCreateUIState()) {}
    }
}