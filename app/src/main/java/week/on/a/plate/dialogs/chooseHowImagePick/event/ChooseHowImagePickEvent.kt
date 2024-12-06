package week.on.a.plate.dialogs.chooseHowImagePick.event

import android.content.Context
import week.on.a.plate.core.Event

sealed class ChooseHowImagePickEvent: Event() {
    data object FromGallery: ChooseHowImagePickEvent()
    data object ByUrl: ChooseHowImagePickEvent()
    data object Close: ChooseHowImagePickEvent()
    data class MakePhoto(val context:Context) : ChooseHowImagePickEvent()
}