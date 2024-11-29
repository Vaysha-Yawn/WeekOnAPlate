package week.on.a.plate.dialogs.chooseHowImagePick.event

import week.on.a.plate.core.Event

sealed class ChooseHowImagePickEvent: Event() {
    data object FromGallery: ChooseHowImagePickEvent()
    data object ByUrl: ChooseHowImagePickEvent()
    data object Close: ChooseHowImagePickEvent()
    data object MakePhoto : ChooseHowImagePickEvent()
}