package week.on.a.plate.menuScreen.logic.eventData

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.Position
import java.time.LocalDate

sealed class DialogMenuData() {

    val show: MutableState<Boolean> = mutableStateOf(true)

    class AddPositionToMenu @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, val recipe: Position.PositionRecipeView,
        val date: LocalDate, val category: String,
    ) : DialogMenuData() {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)
    }

    class MovePositionToMenu @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, val position: Position
    ) : DialogMenuData() {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)
    }

    class DoublePositionToMenu @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, val position: Position
    ) : DialogMenuData() {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)
    }

    class ChangePortionsCount @OptIn(ExperimentalMaterial3Api::class) constructor(
        val recipe: Position.PositionRecipeView,
        val sheetState: SheetState
    ) : DialogMenuData() {
        val portionsCount = mutableIntStateOf(recipe.portionsCount)
    }

    class EditIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val ingredient: Position.PositionIngredientView?,
        val sheetState: SheetState
    ) : DialogMenuData() {
        val ingredientState = mutableStateOf(ingredient?.ingredient?.ingredientView)
        val text = mutableStateOf(ingredient?.ingredient?.description ?: "")
        val count = mutableDoubleStateOf(ingredient?.ingredient?.count ?: 0.0)
    }

    class AddIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val selectionId: Long,
        val sheetState: SheetState
    ) : DialogMenuData() {
        val ingredientState = mutableStateOf<IngredientView?>(null)
        val text = mutableStateOf("")
        val count = mutableDoubleStateOf(0.0)
    }

    class EditNote @OptIn(ExperimentalMaterial3Api::class) constructor(
        val note: Position.PositionNoteView,
        val sheetState: SheetState
    ) : DialogMenuData() {
        val text = mutableStateOf(note.note)
    }

    class AddNote @OptIn(ExperimentalMaterial3Api::class) constructor(
        val selectionId: Long, val sheetState: SheetState
    ) : DialogMenuData() {
        val text = mutableStateOf("")
    }
    class AddPosition(
        val selectionId:Long,
    ) : DialogMenuData()

    class AddPositionNeedSelId() : DialogMenuData()

    // include recipe position
    class EditPosition(
        val position: Position
    ) : DialogMenuData()

    data class ChooseDay
    @OptIn(ExperimentalMaterial3Api::class) constructor(val state: DatePickerState) :
        DialogMenuData()

    data object SelectedToShopList : DialogMenuData()
    data class ToShopList(val recipe: Position.PositionRecipeView) : DialogMenuData()

    class SpecifyDate @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, val eventAfter:(Long)->Unit
    ) : DialogMenuData() {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)
    }
}