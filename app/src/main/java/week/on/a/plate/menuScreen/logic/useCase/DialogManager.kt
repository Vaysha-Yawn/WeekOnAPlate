package week.on.a.plate.menuScreen.logic.useCase

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.menuScreen.logic.eventData.DialogData
import java.util.Stack
import javax.inject.Inject

class DialogManager @Inject constructor() {
    private val dialogStack = Stack<DialogData>()
    val activeDialog = mutableStateOf<DialogData?>(null)

    private fun showTopDialogToUI(dialog:DialogData){
        activeDialog.value = dialog
    }

    private fun clearDialogInUI(){
        activeDialog.value = null
    }

    fun closeDialog() {
        if (dialogStack.isNotEmpty()){
            dialogStack.pop()
            if (dialogStack.isNotEmpty()){
                val next = dialogStack.peek()
                showTopDialogToUI(next)
            }else{
                clearDialogInUI()
            }
        }else{
            clearDialogInUI()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun openDialog(data: DialogData, scope:CoroutineScope){
        showTopDialogToUI(data)
        dialogStack.add(data)

        when(data){
            is DialogData.AddIngredient -> showBottomDialog(data.sheetState, scope)
            is DialogData.AddNote -> showBottomDialog(data.sheetState, scope)
            is DialogData.ChangePortionsCount -> showBottomDialog(data.sheetState, scope)
            is DialogData.EditIngredient -> showBottomDialog(data.sheetState, scope)
            is DialogData.EditNote -> showBottomDialog(data.sheetState, scope)
            else->{}
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun showBottomDialog(state: SheetState, scope:CoroutineScope){
        scope.launch {
            state.show()
        }
    }
}