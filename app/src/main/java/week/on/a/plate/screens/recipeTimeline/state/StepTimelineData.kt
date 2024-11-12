package week.on.a.plate.screens.recipeTimeline.state

import androidx.compose.runtime.MutableState

data class  StepTimelineData(
    val id:Long,
    val description: String,
    val start: MutableState<Long>,
    val duration: MutableState<Long>,
)