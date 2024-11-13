package week.on.a.plate.dialogs.filtersMore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TagBig
import week.on.a.plate.core.uitools.TagSmall
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.dialogs.filtersMore.event.FiltersMoreEvent
import week.on.a.plate.dialogs.filtersMore.logic.FiltersMoreViewModel
import week.on.a.plate.dialogs.sortMore.event.SortMoreEvent
import week.on.a.plate.dialogs.sortMore.logic.SortMoreViewModel

@Composable
fun FilterMoreContent(vm: FiltersMoreViewModel) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)) {
        TextTitle("Дополнительные фильтры", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    vm.state.favoriteIsChecked.value = !vm.state.favoriteIsChecked.value
                }, verticalAlignment = Alignment.CenterVertically

        ) {
            Checkbox(
                checked = vm.state.favoriteIsChecked.value,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.secondary,
                    checkmarkColor = MaterialTheme.colorScheme.onBackground
                ),
                onCheckedChange = { vm.state.favoriteIsChecked.value = !vm.state.favoriteIsChecked.value},
            )
            Spacer(Modifier.width(6.dp))
            TextBody("Только избранные рецепты")
        }
        Spacer(Modifier.height(24.dp))

        val listTime = listOf(
            Pair("до 15 мин", 15),
            Pair("до 30 мин", 30),
            Pair("до 1 ч", 60),
            Pair("до 1 ч и 30 мин", 90),
            Pair("до 2 ч", 120),
        )

        TextBody("Время приготовленмя")
        Spacer(Modifier.height(24.dp))
        TagsListFilterTime(vm.state.allTime, listTime)
        Spacer(Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsListFilterTime(value:MutableIntState, listTags:List<Pair<String,Int>>){
    FlowRow() {
        listTags.forEach {
            TagFilterTime(value.intValue, it.second, it.first){
                if (value.intValue == it.second){
                    value.intValue = 0
                }else{
                    value.intValue = it.second
                }
            }
        }
    }
}

@Composable
fun TagFilterTime(currentValue: Int, targetValue: Int, text: String, onClick: () -> Unit) {
    TagSmall(
        text, modifier = Modifier.clickable {
            onClick()
        }.padding(end = 12.dp, bottom = 12.dp),
        color = if (currentValue == targetValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
        )
}

@Preview(showBackground = true)
@Composable
fun PreviewSortMoreContent() {
    WeekOnAPlateTheme {
        FilterMoreContent(FiltersMoreViewModel())
    }
}