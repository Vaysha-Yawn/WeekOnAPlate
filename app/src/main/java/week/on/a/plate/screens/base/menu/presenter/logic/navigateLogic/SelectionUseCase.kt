package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

class CreateSelIdAndCreatePosOpenDialog @Inject constructor(
    private val getSelOrCreateInDB: GetSelOrCreateInDBUseCase,
    private val addPosition: AddPositionOpenDialog
) {
    suspend operator fun invoke(
        context: Context,
        dateTime: LocalDateTime,
        name: String,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit
    ) {
        val id = scope.async {
            getSelOrCreateInDB(
                dateTime,
                false, name, Locale.getDefault(),
            )
        }
        addPosition(id.await(), context, dialogOpenParams, onEvent)
    }
}

class CreateWeekSelIdAndCreatePosOpenDialog @Inject constructor(
    private val getSelOrCreateInDB: GetSelOrCreateInDBUseCase,
    private val addPosition: AddPositionOpenDialog
) {
    suspend operator fun invoke(
        context: Context,
        date: LocalDate,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit
    ) {
        val id = scope.async(Dispatchers.IO) {
            getSelOrCreateInDB(
                LocalDateTime.of(
                    date,
                    ForWeek.stdTime
                ),
                true, context.getString(ForWeek.fullName), Locale.getDefault(),
            )
        }
        addPosition(id.await(), context, dialogOpenParams, onEvent)
    }
}

class CreateSelectionOpenDialog @Inject constructor(
    private val addSelectionToDB: AddSelectionToDBUseCase,
) {
    suspend operator fun invoke(
        date: LocalDate,
        isForWeek: Boolean,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
    ) {
        val params = EditSelectionViewModel.EditSelectionDialogParams(
            EditSelectionUIState(
                title = R.string.add_meal,
                placeholder = R.string.hint_breakfast
            )
        ) { state ->
            scope.launch {
                val newName = state.text.value
                val time = state.selectedTime.value
                addSelectionToDB(
                    date,
                    newName, Locale.getDefault(), isForWeek, time, scope
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
        scope: CoroutineScope,
    ) {
        if (sel.isForWeek || sel.id == 0L) return
        val params = EditOrDeleteViewModel.EditOrDeleteDialogParams { event ->
            when (event) {
                EditOrDeleteEvent.Close -> {}
                EditOrDeleteEvent.Delete -> scope.launch(Dispatchers.IO) {
                    deleteSelectionInDB(
                        sel,
                        scope
                    )
                }

                EditOrDeleteEvent.Edit -> scope.launch {
                    editSelection(
                        sel,
                        dialogOpenParams,
                        scope
                    )
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
        scope: CoroutineScope,
    ) {
        val oldState = EditSelectionUIState(
            mutableStateOf(sel.name), R.string.edit_meal_name,
            R.string.hint_breakfast
        ).apply {
            this.selectedTime.value = sel.dateTime.toLocalTime()
        }
        val params = EditSelectionViewModel.EditSelectionDialogParams(
            oldState
        ) { state ->
            scope.launch(Dispatchers.IO) {
                editSelectionInDB(
                    sel,
                    state.text.value,
                    state.selectedTime.value, scope
                )
            }
        }
        dialogOpenParams.value = params
    }
}