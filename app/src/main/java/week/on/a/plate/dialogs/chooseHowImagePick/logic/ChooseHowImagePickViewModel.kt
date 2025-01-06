package week.on.a.plate.dialogs.chooseHowImagePick.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.R
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

    fun done(url: String) {
        close()
        resultFlow.value = url
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
        mainViewModel.onEvent(MainEvent.ShowDialog)
    }

    fun onEvent(event: ChooseHowImagePickEvent) {
        when (event) {
            is ChooseHowImagePickEvent.ByUrl -> choosePickImageByUrl()
            ChooseHowImagePickEvent.Close -> close()
            ChooseHowImagePickEvent.FromGallery -> choosePickImageFromFallery()
            is ChooseHowImagePickEvent.MakePhoto -> choosePickImageByMakePhoto(event)
        }
    }

    private fun choosePickImageByMakePhoto(event: ChooseHowImagePickEvent.MakePhoto) {
        mainViewModel.viewModelScope.launch {
            mainViewModel.takePictureUseCase.start(event.contextProvider.provideContext()) { imgPath ->
                done(imgPath ?: "")
            }
        }
    }

    private fun choosePickImageFromFallery() {
        mainViewModel.viewModelScope.launch {
            mainViewModel.imageFromGalleryUseCase.start { imgPath ->
                done(imgPath ?: "")
            }
        }
    }

    private fun choosePickImageByUrl() {
        mainViewModel.viewModelScope.launch {
            val vm = EditOneStringViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                EditOneStringUIState(
                    old ?: "",
                    R.string.edit_image_link,
                    R.string.enter_new_image_link
                )
            ) { url ->
                done(url.lowercase())
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