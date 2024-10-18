package week.on.a.plate.screens.cookPlanner.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import java.time.LocalDate

sealed class CookPlannerEvent : Event() {
    data class CheckStep(val step:RecipeStepView):CookPlannerEvent()//мб другой объект, мб добавить сейчас он checked или нет
    data class StartTimer(val time:Int):CookPlannerEvent()
    data class ShowStepMore(val step:RecipeStepView):CookPlannerEvent()
    data class ChangeTimeStartRecipe(val step:RecipeStepView):CookPlannerEvent()
    data class ChangeTimeEndRecipe(val step:RecipeStepView):CookPlannerEvent()
    data class AddTimeStep(val step:RecipeStepView):CookPlannerEvent()//продлить
    data class MoveTimeStep(val step:RecipeStepView):CookPlannerEvent()//отложить
}