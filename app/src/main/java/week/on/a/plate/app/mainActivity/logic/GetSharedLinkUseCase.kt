package week.on.a.plate.app.mainActivity.logic

import androidx.navigation.NavHostController
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.state.emptyRecipe
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetSharedLinkUseCase @Inject constructor() {

    private var sharedLink = ""
    var isCheckedSharedAction = false

    fun setLink(text: String) {
        sharedLink = text
    }

    fun checkAndStart(use: () -> Unit) {
        if (sharedLink != "" && !isCheckedSharedAction) {
            use()
        }
    }


    fun useSharedLink(nav: NavHostController) {
        isCheckedSharedAction = true
        nav.navigate(
            RecipeCreateDestination(
                null, true,
                emptyRecipe.copy(link = sharedLink)
            )
        )
    }

}
