package week.on.a.plate.menuScreen.logic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.menuScreen.logic.useCase.Navigation
import week.on.a.plate.menuScreen.logic.useCase.SelectedRecipeManager
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    private val navigation: Navigation
) : ViewModel() {
    //states
    val itsDayMenu = mutableStateOf(true)
    val editing = mutableStateOf(false)
    val activeDayInd = mutableIntStateOf(0)

    //data
    val week = sCRUDRecipeInMenu.weekDataExample
    val today = 21

    fun switchWeekOrDayView() {
        selectedRecipeManager.clear()
    }

    fun getCheckState(id: Long): State<Boolean> {
        return selectedRecipeManager.getState(id)
    }

    fun actionNavToFullRecipe(rec: RecipeView) {
        selectedRecipeManager.clear()
        navigation.actionNavToFullRecipe(rec)
    }

    fun actionSwitchEditMode() {
        editing.value = !editing.value
    }

    fun actionCheckRecipe(id: Long) {
        selectedRecipeManager.actionCheckRecipe(id)
    }

    fun actionAddRecipeToCategory(date: Int, category: String) {
        //put in memory date: Int, category: String
        navigation.actionToFindRecipe()

        //sCRUDRecipeInMenu.actionAddRecipeToCategory(date, category)
    }

    fun actionRecipeToNextStep(id: Long) {
        sCRUDRecipeInMenu.actionRecipeToNextStep(id)
    }

    fun actionEdit(id: Long) {
        navigation.actionShowEditDialog(id)
    }

    fun actionChooseAll() {
        selectedRecipeManager.actionChooseAll()
    }

    fun actionDeleteSelected() {
        sCRUDRecipeInMenu.actionDeleteSelected(selectedRecipeManager.getSelected())
    }

    fun actionSelectedToShopList() {
        navigation.actionSelectedToShopList(selectedRecipeManager.getSelected())
    }


}