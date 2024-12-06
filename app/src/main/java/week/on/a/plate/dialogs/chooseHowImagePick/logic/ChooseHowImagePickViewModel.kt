package week.on.a.plate.dialogs.chooseHowImagePick.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.chooseHowImagePick.event.ChooseHowImagePickEvent
import week.on.a.plate.dialogs.chooseHowImagePick.state.ChooseHowImagePickUIState
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class ChooseHowImagePickViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = ChooseHowImagePickUIState()
    private lateinit var resultFlow: MutableStateFlow<String?>
    private var old = ""

    fun start(): Flow<String?> {
        val flow = MutableStateFlow<String?>(null)
        resultFlow = flow
        return flow
    }

    fun done(event: String) {
        close()
        resultFlow.value = event
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
        mainViewModel.onEvent(MainEvent.ShowDialog)
    }

    fun onEvent(event: ChooseHowImagePickEvent) {
        when (event) {
            ChooseHowImagePickEvent.ByUrl -> {
                mainViewModel.viewModelScope.launch {
                    val vm = EditOneStringViewModel()
                    vm.mainViewModel = mainViewModel
                    mainViewModel.onEvent(MainEvent.OpenDialog(vm))
                    vm.launchAndGet(
                        EditOneStringUIState(
                            old ?: "",
                            "Редактировать ссылку на изображение",
                            "Введите новую ссылку на изображение"
                        )
                    ) { note ->
                        done(note.lowercase())
                    }
                }
            }

            ChooseHowImagePickEvent.Close -> close()
            ChooseHowImagePickEvent.FromGallery -> {
                mainViewModel.viewModelScope.launch {
                    mainViewModel.imageFromGalleryUseCase.start { imgPath ->
                        done(imgPath ?: "")
                    }
                }
            }

            is ChooseHowImagePickEvent.MakePhoto -> {
                mainViewModel.viewModelScope.launch {
                    mainViewModel.takePictureUseCase.start(event.context) { imgPath ->
                        done(imgPath ?: "")
                    }
                }
            }
        }
    }

    suspend fun launchAndGet(oldValue : String?, use: (String) -> Unit) {
        old = oldValue?:""
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}