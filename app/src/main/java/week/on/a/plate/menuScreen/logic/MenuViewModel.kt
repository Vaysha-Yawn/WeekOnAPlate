package week.on.a.plate.menuScreen.logic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.example.EmptyWeek
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
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

    val week: MutableStateFlow<WeekView> = MutableStateFlow<WeekView>(EmptyWeek)
    val today = LocalDate.now()

    init {
        viewModelScope.launch {
           // sCRUDRecipeInMenu.menuR.insertNewWeek(WeekDataExample)
            sCRUDRecipeInMenu.menuR.getCurrentWeek(today)
                .collect {
                    week.value = it
                }
        }
    }

    //states
    val itsDayMenu = mutableStateOf(true)
    val editing = mutableStateOf(false)
    val activeDayInd = mutableIntStateOf(0)

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