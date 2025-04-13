package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.imageFromGallery.getSavedPicture
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.logic.ChooseHowImagePickViewModel
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import javax.inject.Inject

class RecipeCreateImageUseCase @Inject constructor() {

    private fun getImage(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        oldValue: String?,
        use: (String) -> Unit,
    ) {
        val params = ChooseHowImagePickViewModel.ChooseHowImagePickDialogParams(oldValue, use)
        dialogOpenParams.value = params
    }

    suspend fun editMainImage(
        state: RecipeCreateUIState,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        event: RecipeCreateEvent.EditMainImage
    ) = coroutineScope {
        getImage(dialogOpenParams, state.photoLink.value) { nameImage ->
            launch {
                state.photoLink.value = nameImage
                if (!nameImage.startsWith("http")) {
                    val picture = getSavedPicture(event.context, nameImage)
                    state.mainImageContainer.value = picture?.asImageBitmap()
                }
            }
        }
    }

    suspend fun editImage(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        event: RecipeCreateEvent.EditImage
    ) = coroutineScope {
        getImage(dialogOpenParams, event.recipeStepState.image.value) { nameImage ->
            launch {
                event.recipeStepState.image.value = nameImage
                if (!nameImage.startsWith("http")) {
                    val picture = getSavedPicture(event.context, nameImage)
                    event.recipeStepState.imageContainer.value = picture?.asImageBitmap()
                }
            }
        }
    }

    fun deleteImage(event: RecipeCreateEvent.DeleteImage) {
        event.recipeStepState.image.value = ""
    }
}