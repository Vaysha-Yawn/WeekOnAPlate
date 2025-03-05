package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.logic.ChooseHowImagePickViewModel
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.logic.imageFromGallery.getSavedPicture
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState

class RecipeCreateImageUseCase(val mainViewModel: MainViewModel, val state: RecipeCreateUIState) {

    private fun getImage(oldValue: String?, use: (String) -> Unit) {
        ChooseHowImagePickViewModel.launch(mainViewModel, oldValue, use)
    }

    fun editMainImage(event: week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditMainImage) {
        getImage(state.photoLink.value) { nameImage ->
            mainViewModel.viewModelScope.launch {
                state.photoLink.value = nameImage
                if (!nameImage.startsWith("http")) {
                    val picture = getSavedPicture(event.context, nameImage)
                    state.mainImageContainer.value = picture?.asImageBitmap()
                }
            }
        }
    }

    fun editImage(event: week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditImage) {
        getImage(event.recipeStepState.image.value) { nameImage ->
            mainViewModel.viewModelScope.launch {
                event.recipeStepState.image.value = nameImage
                if (!nameImage.startsWith("http")) {
                    val picture = getSavedPicture(event.context, nameImage)
                    event.recipeStepState.imageContainer.value = picture?.asImageBitmap()
                }
            }
        }
    }

    fun deleteImage(event: week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.DeleteImage) {
        event.recipeStepState.image.value = ""
    }
}