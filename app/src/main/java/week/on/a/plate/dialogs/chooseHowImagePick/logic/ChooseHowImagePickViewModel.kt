package week.on.a.plate.dialogs.chooseHowImagePick.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.dialogs.chooseHowImagePick.event.ChooseHowImagePickEvent
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel

class ChooseHowImagePickViewModel(
    val mainViewModel: MainViewModel,
    oldValue: String?,
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    useResult: (String) -> Unit,
) : DialogViewModel<String>(
    viewModelScope,
    openDialog,
    closeDialog,
    useResult
) {
    private var old = oldValue ?: ""

    fun onEvent(event: ChooseHowImagePickEvent) {
        when (event) {
            is ChooseHowImagePickEvent.ByUrl -> choosePickImageByUrl()
            ChooseHowImagePickEvent.Close -> {
                close()
                mainViewModel.onEvent(MainEvent.ShowDialog)
            }

            ChooseHowImagePickEvent.FromGallery -> choosePickImageFromGallery()
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

    private fun choosePickImageFromGallery() {
        mainViewModel.viewModelScope.launch {
            mainViewModel.imageFromGalleryUseCase.start { imgPath ->
                done(imgPath ?: "")
            }
        }
    }

    private fun choosePickImageByUrl() {
        mainViewModel.viewModelScope.launch {
            EditOneStringViewModel.launch(mainViewModel, EditOneStringUIState(
                old,
                R.string.edit_image_link,
                R.string.enter_new_image_link
            )){ url ->
                done(url.lowercase())
            }
        }
    }

    companion object {
        fun launch(
            mainViewModel: MainViewModel,
            oldValue: String?,
            useResult: (String) -> Unit,
        ) {
            ChooseHowImagePickViewModel(
                mainViewModel,
                oldValue,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}