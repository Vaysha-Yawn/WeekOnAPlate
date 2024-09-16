package week.on.a.plate.menuScreen.data.eventData

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.R
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.week.Position

sealed class DialogData(val onEvent: (MenuEvent) -> Unit) {

    val show: MutableState<Boolean> = mutableStateOf(true)
    val close = {
        onEvent(MenuEvent.CloseDialog)
    }

    class ChangePortionsCount @OptIn(ExperimentalMaterial3Api::class) constructor(
        val recipe: Position.PositionRecipeView,
        val sheetState: SheetState, onEvent: (MenuEvent) -> Unit,
    ) : DialogData(onEvent) {
        val portionsCount = mutableIntStateOf(recipe.portionsCount)
        val done = {
            onEvent(MenuEvent.ActionDBMenu(ActionMenuDBData.ChangePortionsCountDB(this)))
            onEvent(MenuEvent.CloseDialog)
        }
    }


    class EditIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val ingredient: Position.PositionIngredientView?,
        val sheetState: SheetState, onEvent: (MenuEvent) -> Unit,
    ) : DialogData(onEvent) {
        val ingredientState = mutableStateOf(ingredient?.ingredient?.ingredientView)
        val text = mutableStateOf(ingredient?.ingredient?.description ?: "")
        val count = mutableDoubleStateOf(ingredient?.ingredient?.count ?: 0.0)
        val doneBtnText = R.string.apply

        val done = {
            onEvent(MenuEvent.ActionDBMenu(ActionMenuDBData.EditIngredientDB(this)))
            onEvent(MenuEvent.CloseDialog)
        }

        val chooseIngredient = {
            onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.NavToChooseIngredient))
        }
    }

    class AddIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val selectionId: Long,
        val sheetState: SheetState, onEvent: (MenuEvent) -> Unit,
    ) : DialogData(onEvent) {
        val ingredientState = mutableStateOf<IngredientView?>(null)
        val text = mutableStateOf("")
        val count = mutableDoubleStateOf(0.0)
        val doneBtnText = R.string.add

        val done = {
            onEvent(MenuEvent.ActionDBMenu(ActionMenuDBData.AddIngredientDB(this)))
            onEvent(MenuEvent.CloseDialog)
        }

        val chooseIngredient = {
            onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.NavToChooseIngredient))
        }
    }

    //


    class EditNote @OptIn(ExperimentalMaterial3Api::class) constructor(
        val note: Position.PositionNoteView,
        val sheetState: SheetState, onEvent: (MenuEvent) -> Unit,
    ) : DialogData(onEvent) {
        val text = mutableStateOf(note.note)
    }

    class AddNote @OptIn(ExperimentalMaterial3Api::class) constructor(
        val selectionId: Long, val sheetState: SheetState, val onEventVM: (MenuEvent) -> Unit
    ) : DialogData(onEventVM) {
        val text = mutableStateOf("")

        val done = {
            onEventVM(MenuEvent.ActionDBMenu(ActionMenuDBData.AddNoteDB(text.value, selectionId)))
            onEventVM(MenuEvent.CloseDialog)
        }
    }

    //

    class AddPosition(
        val selectionId: Long, onEvent: (MenuEvent) -> Unit,
    ) : DialogData(onEvent)

    // include recipe position
    class EditPosition(
        val position: Position, onEvent: (MenuEvent) -> Unit,
    ) : DialogData(onEvent)

    class ChooseDay @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, onEventVM: (MenuEvent) -> Unit,
    ) :
        DialogData(onEventVM)

    class SelectedToShopList(onEventVM: (MenuEvent) -> Unit) : DialogData(onEventVM)
    class ToShopList(val recipe: Position.PositionRecipeView, onEventVM: (MenuEvent) -> Unit) :
        DialogData(onEventVM)


    //todo wrong module, please move*******************************************************************************************
    class SelectIngredients @OptIn(ExperimentalMaterial3Api::class) constructor(
        val sheetState: SheetState,
        val onEventVM: (MenuEvent) -> Unit,
        val done: (List<IngredientView>) -> Unit,
    ) : DialogData(onEventVM) {
        val search = mutableStateOf<String>("")
        val list = mutableStateOf(listOf<IngredientView>())

        // ействие вызывается при нажатии на кнопку создать новый ингредиент
        @OptIn(ExperimentalMaterial3Api::class)
        val createIngredient = {
            val createDialogData =
                CreateIngredient(search.value, sheetState, onEventVM) { ingredient ->
                    list.value = list.value.toMutableList().apply {
                        this.add(ingredient)
                    }.toList()
                }
            onEventVM(MenuEvent.OpenDialog(createDialogData))
        }
    }

    class FindIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val sheetState: SheetState,
        val onEventVM: (MenuEvent) -> Unit,
        val done: (IngredientView) -> Unit,
    ) : DialogData(onEventVM) {
        val search = mutableStateOf<String>("")

        @OptIn(ExperimentalMaterial3Api::class)
        val createIngredient = {
            val createDialogData =
                CreateIngredient(search.value, sheetState, onEventVM) { ingredient ->
                    //close this
                    done(ingredient)
                }
            onEventVM(MenuEvent.OpenDialog(createDialogData))
        }
    }

    class CreateIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        startValue: String,
        val sheetState: SheetState,
        val onEventVM: (MenuEvent) -> Unit,
        val doneExtra: (IngredientView) -> Unit
    ) : DialogData(onEventVM) {
        val name = mutableStateOf<String>(startValue)
        val measure = mutableStateOf<String>("")
        val done = {
            this.close()
            val ingredient = IngredientView(0, "", name.value, measure.value)
            // add toBD and close ingredient, get id and change in ingredientView
            // done with fresh data, fresh id
            doneExtra(ingredient)
        }
    }

    class SelectCategoryIngredient(onEventVM: (MenuEvent) -> Unit) : DialogData(onEventVM) {
        val search = mutableStateOf<String>("")
    }

    class CreateCategoryIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val sheetState: SheetState, onEventVM: (MenuEvent) -> Unit,
    ) : DialogData(onEventVM) {
        val text = mutableStateOf<String>("")
    }
}