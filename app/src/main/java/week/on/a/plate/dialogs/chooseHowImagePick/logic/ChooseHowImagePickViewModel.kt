package week.on.a.plate.dialogs.chooseHowImagePick.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.chooseHowImagePick.event.ChooseHowImagePickEvent
import week.on.a.plate.dialogs.chooseHowImagePick.state.ChooseHowImagePickUIState
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.exitApply.state.ExitApplyUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class ChooseHowImagePickViewModel(): DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = ChooseHowImagePickUIState()
    private lateinit var resultFlow: MutableStateFlow< ChooseHowImagePickEvent?>

    fun start(): Flow<ChooseHowImagePickEvent?> {
        val flow = MutableStateFlow<ChooseHowImagePickEvent?>(null)
        resultFlow = flow
        return flow
    }

     fun done(event: ChooseHowImagePickEvent) {
         close()
         resultFlow.value = event
    }

     fun close(){
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: ChooseHowImagePickEvent){
        when(event) {
            ChooseHowImagePickEvent.ByUrl -> done(event)
            ChooseHowImagePickEvent.Close -> close()
            ChooseHowImagePickEvent.FromGallery -> done(event)
        }
    }

    suspend fun launchAndGet( use: (ChooseHowImagePickEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}