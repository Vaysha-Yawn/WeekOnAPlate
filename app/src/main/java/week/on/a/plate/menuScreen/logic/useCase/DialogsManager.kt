package week.on.a.plate.menuScreen.logic.useCase

import week.on.a.plate.menuScreen.logic.eventData.DialogEvent
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.stateData.MenuIUState
import java.util.Stack

// todo управление очередью диалогов
class DialogsManager() {

    val stack = Stack<DialogEvent>()

    fun addDialog(dialog: DialogMenuData, doneAction: MenuEvent, uiMenuIUState: MenuIUState){
        stack.push(DialogEvent(dialog, doneAction))
        uiMenuIUState.dialogState.value = dialog
    }

    fun clickDoneForActiveDialog(uiMenuIUState: MenuIUState, onEvent:(MenuEvent)->Unit){
        val active = stack.pop()

        if (!stack.empty()){

            stack.forEach { DEvent->
                if (DEvent.doneAction == active.doneAction){
                    stack.remove(DEvent)

                }
            }
        }else{
            uiMenuIUState.dialogState.value = null
        }

        onEvent(active.doneAction)
    }

}