package week.on.a.plate.screens.additional.tutorial.event

import week.on.a.plate.core.Event

sealed interface TutorialEvent : Event {
    class SelectPage(val i: Int) : TutorialEvent
    object NextPage : TutorialEvent
    object LastPage : TutorialEvent
    object Skip : TutorialEvent
    object Done : TutorialEvent
}