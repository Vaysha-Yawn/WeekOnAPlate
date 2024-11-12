package week.on.a.plate.screens.recipeTimeline.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf

data class RecipeTimelineUIState(
    val allUISteps: MutableState<List<StepTimelineData>>,
    var plannedAllTime: Int
) {
    val activeStepInd: MutableState<Int> = mutableIntStateOf(0)
    val realAllTime: MutableState<Long> = mutableLongStateOf(getMaxTime())

    private fun getMaxTime(): Long {
       return if (allUISteps.value.isEmpty()) 0 else allUISteps.value.maxOf { it.start.value+it.duration.value }
    }

    companion object{
        fun getNewStepTimelineDataObj(descr:String): StepTimelineData {
            return StepTimelineData(
                0,
                descr,
                mutableLongStateOf(0),
                mutableLongStateOf(5*60)
            )
        }
    }

}