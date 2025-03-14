package week.on.a.plate.screens.additional.createRecipe.view.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.BackButton
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState

@Composable
fun TopBarRecipeCreate(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton {
            onEvent(RecipeCreateEvent.Close)
        }
        TextBody(text =
        if (state.isForCreate.value){
            stringResource(R.string.recipe_creation)
        }else{
            stringResource(R.string.recipe_edition)
        }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            painter = painterResource(id = R.drawable.check),
            contentDescription = "Done",
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
                .clickable {
                    onEvent(RecipeCreateEvent.Done)
                }
                .size(36.dp)
        )
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBarRecipeCreate() {
    WeekOnAPlateTheme {
        Column {
            TopBarRecipeCreate(RecipeCreateUIState()) {}
        }
    }
}