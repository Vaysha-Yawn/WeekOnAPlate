package week.on.a.plate.screens.recipeTimeline.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.ColorStrokeGrey
import week.on.a.plate.core.theme.ColorSubTextGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.data.dataView.example.recipeExampleBase
import week.on.a.plate.screens.recipeTimeline.event.RecipeTimelineEvent
import week.on.a.plate.screens.recipeTimeline.logic.RecipeTimelineViewModel
import week.on.a.plate.screens.recipeTimeline.state.RecipeTimelineUIState

@Composable
fun RecipeTimelineStart(vm: RecipeTimelineViewModel) {
    val state = vm.state
    val onEvent = { event: Event ->
        vm.onEvent(event)
    }
    RecipeTimelineContent(state, onEvent)
}

@Composable
fun RecipeTimelineContent(state: RecipeTimelineUIState, onEvent: (Event) -> Unit) {

    val titlePage = "Настройка времени приготовления"

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CloseButton {
                onEvent(RecipeTimelineEvent.Back)
            }
            TextTitleItalic(titlePage, textAlign = TextAlign.End)
        }

        Empty()
        LazyColumn(Modifier.weight(1f)) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.background,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                ) {
                    TextBody(
                        (state.activeStepInd.value + 1).toString() + " шаг",
                        Modifier.padding(bottom = 5.dp).background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(5.dp)).padding(horizontal = 5.dp)
                    )
                    TextBody(state.allUISteps.value[state.activeStepInd.value].description)
                }

                Empty()

                TextSmall(
                    "Ожидаемое время приготовления: ${state.plannedAllTime.timeToString()}",
                    color = ColorSubTextGrey
                )
                TextSmall(
                    "Фактическое время приготовления: ${state.realAllTime.value.toInt().timeToString()}",
                    color = ColorSubTextGrey
                )

                Empty()
            }
        }

        Box(Modifier.weight(1f)) {
            Timeline(state) {
                onEvent(RecipeTimelineEvent.SelectStep(it))
            }
        }

        Empty()
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircleButton(R.drawable.duration, "Длительность", MaterialTheme.colorScheme.secondary, this) {
                onEvent(RecipeTimelineEvent.SetDuration)
            }
            CircleButton(R.drawable.target, "Указать начало", rowScope = this) {
                onEvent(RecipeTimelineEvent.SetStart)
            }
            CircleButton(R.drawable.auto, "Авто", rowScope = this) {
                onEvent(RecipeTimelineEvent.Auto)
            }
        }
        Empty()
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircleButton(
                R.drawable.parallel,
                "Начать вместе с N",
                rowScope = this
            ) {
                onEvent(RecipeTimelineEvent.StartWithN)
            }
            CircleButton(R.drawable.after_n, "После N", rowScope = this) {
                onEvent(RecipeTimelineEvent.AfterN)
            }
            CircleButton(R.drawable.after_last, "После предыдущего", rowScope = this) {
                onEvent(RecipeTimelineEvent.AfterLast)
            }
            CircleButton(R.drawable.after_lasts, "После предыдущих", rowScope = this) {
                onEvent(RecipeTimelineEvent.AfterLasts)
            }
            CircleButton(R.drawable.to_end, "В конец", rowScope = this) {
                onEvent(RecipeTimelineEvent.ToEnd)
            }
        }
        Empty()
        DoneButton(stringResource(R.string.done)) {
            onEvent(RecipeTimelineEvent.Done)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Timeline(state: RecipeTimelineUIState, click: (Int) -> Unit) {
    val horizontalScroll = rememberScrollState()
    val zoom = 10
    val cellSize = 40
    val cellSizePerZoom = cellSize.toFloat() / zoom.toFloat()
    val countMax = state.allUISteps.value.maxOf { it.start.value + it.duration.value }
    Row(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize()
                .horizontalScroll(horizontalScroll)
        ) {
            stickyHeader {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp).background(MaterialTheme.colorScheme.surface)) {
                    var n = 0
                    for (i in 0 .. countMax / zoom) {
                        TextSmall(
                            (n * zoom).toString(),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .offset(x = (n * cellSize).dp + 70.dp - 8.dp)
                        )
                        n += 1
                    }
                }
            }
            items(state.allUISteps.value.size) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val cellCount = countMax / zoom
                            var x = 70f * this.density
                            for (i in 0 .. cellCount) {
                                var y = 0f
                                val size = 14f
                                val count = (this.size.height / size).toInt()
                                for (yn in 0 until count) {
                                    this.drawCircle(ColorStrokeGrey, 2f, center = Offset(x, y))
                                    y += size
                                }
                                x += cellSize * this.density
                            }
                        }) {
                    val step = state.allUISteps.value[it]
                    TextBody(
                        (it + 1).toString() + " шаг",
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .width(70.dp)
                    )
                    Spacer(
                        Modifier
                            .width((step.start.value.toFloat()/60 * cellSizePerZoom).dp)
                            .animateContentSize()
                    )
                    TextBody(
                        (it + 1).toString(), textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width((step.duration.value.toFloat() * cellSizePerZoom/60).dp)
                            .animateContentSize()
                            .background(
                                if (state.activeStepInd.value == it) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                click(it)
                            }
                    )
                    Spacer(Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun Empty() {
    Spacer(Modifier.size(24.dp))
}

@Composable
fun CircleButton(
    imgRec: Int,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    rowScope: RowScope,
    click: () -> Unit,
) {
    with(rowScope){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f).clickable {
            click()
        }) {
            Icon(
                painterResource(imgRec),
                text,
                Modifier
                    .background(color, CircleShape)
                    .padding(10.dp)
                    .size(24.dp))
            Spacer(Modifier.height(5.dp))
            TextInApp(
                text,
                Modifier,
                textStyle = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                    fontSize = 10.sp,
                    lineHeight = 12.sp,
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign =  TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeTimelinePreview() {
    WeekOnAPlateTheme {
        val allSteps = remember {
            mutableStateOf(
                recipeExampleBase[1].steps.map {
                    RecipeTimelineUIState.getNewStepTimelineDataObj(it.description) }) }
        RecipeTimelineContent(RecipeTimelineUIState(
            allSteps,
            plannedAllTime = 25,
        )){}
    }
}