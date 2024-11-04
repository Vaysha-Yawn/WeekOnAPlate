package week.on.a.plate.screens.tutorial.event

import week.on.a.plate.core.Event

sealed class TutorialEvent:Event() {
    data object NextPage:TutorialEvent()
    data object LastPage:TutorialEvent()
    data object Skip:TutorialEvent()
    data object Done:TutorialEvent()
}