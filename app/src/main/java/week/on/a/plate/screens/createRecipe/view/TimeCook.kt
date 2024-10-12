package week.on.a.plate.screens.createRecipe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

@Composable
fun TimeCookRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextBody(text = "Активное время")
            Spacer(modifier = Modifier.height(5.dp))
            TimerButton(state.prepTime.intValue){
                onEvent(RecipeCreateEvent.EditActiveTime)
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextBody(text = "Время всего")
            Spacer(modifier = Modifier.height(5.dp))
            TimerButton(state.allTime.intValue){
                onEvent(RecipeCreateEvent.EditAllTime)
            }
        }
    }
}

@Composable
fun TimerButton(time:Int, edit:()->Unit){
    Row(
        Modifier
            .clickable {
                edit()
            }
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.timer), contentDescription = "")
        Spacer(modifier = Modifier.width(5.dp))
        TextBody(text = time.timeToString())
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTimeCookRecipeEdit() {
    WeekOnAPlateTheme {
        TimeCookRecipeEdit(RecipeCreateUIState()) {}
    }
}