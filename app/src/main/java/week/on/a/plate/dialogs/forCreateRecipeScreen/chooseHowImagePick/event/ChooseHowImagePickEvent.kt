package week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event

import android.content.Context
import week.on.a.plate.core.Event

sealed class ChooseHowImagePickEvent: Event() {
    data object FromGallery: ChooseHowImagePickEvent()
    data object ByUrl: ChooseHowImagePickEvent()
    data object Close: ChooseHowImagePickEvent()
    data class MakePhoto(val contextProvider: ContextProvider) : ChooseHowImagePickEvent()
}

interface ContextProvider {
    fun provideContext():Context
}

class BaseContextProvider(val context: Context) : ContextProvider {
    override fun provideContext() = context
}