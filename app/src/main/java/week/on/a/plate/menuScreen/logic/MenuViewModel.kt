package week.on.a.plate.menuScreen.logic

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.menuScreen.logic.useCase.Navigation
import week.on.a.plate.menuScreen.logic.useCase.SelectedRecipeManager
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    private val navigation: Navigation
) : ViewModel() {

    val today = LocalDate.now()
    init {
        viewModelScope.launch {
            val testData = sCRUDRecipeInMenu.menuR.getCurrentWeek(today)
        }
    }

    //states
    val itsDayMenu = mutableStateOf(true)
    val editing = mutableStateOf(false)
    val activeDayInd = mutableIntStateOf(0)

    //data
    val week = mutableStateOf(WeekDataExample)

    fun switchWeekOrDayView() {
        selectedRecipeManager.clear()
    }

    fun getCheckState(id: Long): State<Boolean> {
        return selectedRecipeManager.getState(id)
    }

    fun actionNavToFullRecipe(rec: RecipeShortView) {
        selectedRecipeManager.clear()
        navigation.actionNavToFullRecipe(rec)
    }

    fun actionSwitchEditMode() {
        editing.value = !editing.value
    }

    fun actionCheckRecipe(id: Long) {
        selectedRecipeManager.actionCheckRecipe(id)
    }

    fun actionAddRecipeToCategory(date: LocalDate, category: String) {
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