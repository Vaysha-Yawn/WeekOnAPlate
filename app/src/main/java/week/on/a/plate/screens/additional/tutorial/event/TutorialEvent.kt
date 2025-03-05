package week.on.a.plate.screens.additional.tutorial.event

import week.on.a.plate.core.Event

sealed class TutorialEvent:Event() {
    class SelectPage(i: Int) : TutorialEvent()
    data object NextPage : TutorialEvent()
    data object LastPage : TutorialEvent()
    data object Skip : TutorialEvent()
    data object Done : TutorialEvent()
}