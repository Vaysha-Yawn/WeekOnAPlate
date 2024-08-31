package week.on.a.plate.menuScreen.view.editDialogs

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.DayInWeekData
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.TextInAppColoredWithBorder
import week.on.a.plate.core.uitools.buttons.ButtonText
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.view.calendar.BlockCalendar
import week.on.a.plate.ui.theme.ColorPanel
import week.on.a.plate.ui.theme.ColorSecond
import week.on.a.plate.ui.theme.ColorTransparent
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import java.time.LocalDate

@Composable
fun PlaceRecipe(
    days: MutableList<DayView>,
    selectedDay: DayView,
    done:(day:DayView, category:String)->Unit
) {
    val ind = days.indexOfFirst { pair -> pair == selectedDay }
    val activeDate = remember {
        mutableStateOf(ind)
    }
    val selectedCategory = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(20.dp)) {
        TextInApp(
            "Дублировать Паста в...",
            textStyle = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(50.dp))

        BlockCalendar(
            days,
            LocalDate.now(), activeDate.value
        ) { activeInd->
            activeDate.value = activeInd
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            for (i in days[activeDate.value].selections){
                val backgroundColor = if (i.category==selectedCategory.value) ColorSecond else ColorTransparent
                TextInAppColoredWithBorder(
                    i.category, modifier = Modifier
                        .clickable {
                            selectedCategory.value = i.category
                        }, colorBackground = backgroundColor, borderColor = ColorSecond
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        DoneButton() {
            done(days[activeDate.value], selectedCategory.value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlaceRecipe() {
    WeekOnAPlateTheme {
        PlaceRecipe(WeekDataExample.days, WeekDataExample.days[0]){d,s->}
    }
}