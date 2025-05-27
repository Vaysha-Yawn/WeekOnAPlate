package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogs.editOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.dialogs.editSelectionDialog.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelectionDialog.state.EditSelectionUIState
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddSelectionToDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteSelectionInDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.EditSelectionInDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.GetSelOrCreateInDBUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition.AddPositionOpenDialog
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject

class CreateWeekSelIdAndCreatePosOpenDialog @Inject constructor(
    private val getSelOrCreateInDB: GetSelOrCreateInDBUseCase,
    private val addPosition: AddPositionOpenDialog
) {
    suspend operator fun invoke(
        context: Context,
        activeDay: MutableState<LocalDate>,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        launch(Dispatchers.IO) {
            val id = async {
                getSelOrCreateInDB(
                    LocalDateTime.of(
                        activeDay.value,
                        ForWeek.stdTime
                    ),
                    true, context.getString(ForWeek.fullName), Locale.getDefault(),
                )
            }
            addPosition(id.await(), context, dialogOpenParams, onEvent)
        }
    }
}

class CreateSelectionOpenDialog @Inject constructor(
    private val addSelectionToDB: AddSelectionToDBUseCase,
) {
    suspend operator fun invoke(
        date: LocalDate,
        isForWeek: Boolean,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) = coroutineScope {
        val params = EditSelectionViewModel.EditSelectionDialogParams(
            EditSelectionUIState(
                title = R.string.add_meal,
                placeholder = R.string.hint_breakfast
            )
        ) { state ->
            launch(Dispatchers.IO) {
                val newName = state.text.value
                val time = state.selectedTime.value
                addSelectionToDB(
                    date,
                    newName, Locale.getDefault(), isForWeek, time
                )
            }
        }
        dialogOpenParams.value = params
    }
}

class EditOrDeleteSelectionOpenDialog @Inject constructor(
    private val deleteSelectionInDB: DeleteSelectionInDBUseCase,
    private val editSelection: EditSelectionOpenDialog,
) {
    suspend operator fun invoke(
        sel: SelectionView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) =
        coroutineScope {
            if (sel.isForWeek || sel.id == 0L) return@coroutineScope
            val params = EditOrDeleteViewModel.EditOrDeleteDialogParams { event ->
                launch(Dispatchers.IO) {
                    when (event) {
                        EditOrDeleteEvent.Close -> {}
                        EditOrDeleteEvent.Delete -> deleteSelectionInDB(sel)
                        EditOrDeleteEvent.Edit -> editSelection(sel, dialogOpenParams)
                    }
                }
            }
            dialogOpenParams.value = params
        }
}

class EditSelectionOpenDialog @Inject constructor(
    private val editSelectionInDB: EditSelectionInDBUseCase,
) {
    suspend operator fun invoke(
        sel: SelectionView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) =
        coroutineScope {
            val oldState = EditSelectionUIState(
                mutableStateOf(sel.name), R.string.edit_meal_name,
                R.string.hint_breakfast
            ).apply {
                this.selectedTime.value = sel.dateTime.toLocalTime()
            }
            val params = EditSelectionViewModel.EditSelectionDialogParams(
                oldState
            ) { state ->
                launch(Dispatchers.IO) {
                    editSelectionInDB(
                        sel,
                        state.text.value,
                        state.selectedTime.value
                    )
                }
            }
            dialogOpenParams.value = params
        }
}