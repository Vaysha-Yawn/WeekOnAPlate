package week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event

import android.content.Context
import week.on.a.plate.core.Event

sealed interface ChooseHowImagePickEvent : Event {
    object FromGallery : ChooseHowImagePickEvent
    object ByUrl : ChooseHowImagePickEvent
    object Close : ChooseHowImagePickEvent
    class MakePhoto(val contextProvider: ContextProvider) : ChooseHowImagePickEvent
}

interface ContextProvider {
    fun provideContext(): Context
}

class BaseContextProvider(val context: Context) : ContextProvider {
    override fun provideContext() = context
}