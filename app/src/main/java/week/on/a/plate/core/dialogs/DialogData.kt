package week.on.a.plate.core.dialogs

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.R
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.tools.dateToLocalDate
import week.on.a.plate.menuScreen.data.eventData.ActionWeekMenuDB
import week.on.a.plate.menuScreen.data.eventData.MenuEvent


sealed class DialogData(val onEvent: (MainEvent) -> Unit) {

    val show: MutableState<Boolean> = mutableStateOf(true)
    val close = {
        show.value = false
        onEvent(MainEvent.CloseDialog)
    }

    class SelectedFilterTags @OptIn(ExperimentalMaterial3Api::class) constructor(
        val tags: List<RecipeTagView>,
        val ingredients: List<IngredientView>,
        val sheetState: SheetState, onEvent: (MainEvent) -> Unit,
    ) : DialogData(onEvent)

    //
    class ChangePortionsCount @OptIn(ExperimentalMaterial3Api::class) constructor(
        val recipe: Position.PositionRecipeView,
        val sheetState: SheetState, onEvent: (MainEvent) -> Unit,
    ) : DialogData(onEvent) {
        val portionsCount = mutableIntStateOf(recipe.portionsCount)
        val done = {
         //   onEvent(MainEvent.ActionDBMenu(ActionWeekMenuDB.ChangePortionsCountDB(this)))
            close()
        }
    }

    //
    class EditIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val ingredient: Position.PositionIngredientView?,
        val sheetState: SheetState, onEvent: (MainEvent) -> Unit,
    ) : DialogData(onEvent) {
        val ingredientState = mutableStateOf(ingredient?.ingredient?.ingredientView)
        val text = mutableStateOf(ingredient?.ingredient?.description ?: "")
        val count = mutableDoubleStateOf(ingredient?.ingredient?.count ?: 0.0)
        val doneBtnText = R.string.apply

        val done = {
           // onEvent(MainEvent.ActionDBMenu(ActionWeekMenuDB.EditIngredientPositionDB(this)))
            close()
        }

        val chooseIngredient = {
            //todo
           // onEvent(MainEvent.NavigateFromMenu(NavFromMenuData.NavToChooseIngredient))
        }
    }

    class AddIngredientPosition @OptIn(ExperimentalMaterial3Api::class) constructor(
        val selectionId: Long,
        val sheetState: SheetState, onEvent: (MainEvent) -> Unit,
    ) : DialogData(onEvent) {
        val ingredientState = mutableStateOf<IngredientView?>(null)
        val text = mutableStateOf("")
        val count = mutableDoubleStateOf(0.0)
        val doneBtnText = R.string.add

        val done = {
         //   onEvent(MainEvent.ActionDBMenu(ActionWeekMenuDB.AddIngredientPositionDB(this)))
            close()
        }

        val chooseIngredient = {
            //todo
           // onEvent(MainEvent.Navigate())
        }
    }

    //
    class EditNote @OptIn(ExperimentalMaterial3Api::class) constructor(
        val note: Position.PositionNoteView,
        val sheetState: SheetState, onEvent: (MainEvent) -> Unit,
    ) : DialogData(onEvent) {
        val text = mutableStateOf(note.note)
        val done = {
           // onEvent(MainEvent.ActionDBMenu(ActionWeekMenuDB.EditNoteDB(this)))
           close()
        }
    }

    class AddNote @OptIn(ExperimentalMaterial3Api::class) constructor(
        val selectionId: Long, val sheetState: SheetState, val onEventVM: (MainEvent) -> Unit
    ) : DialogData(onEventVM) {
        val text = mutableStateOf("")

        val done = {
            onEventVM(MainEvent.ActionDBMenu(ActionWeekMenuDB.AddNoteDB(text.value, selectionId)))
            onEventVM(MainEvent.CloseDialog)
        }
    }

    //

    class AddPosition(
        val selectionId: Long, onEvent: (MainEvent) -> Unit,
    ) : DialogData(onEvent)

    // include recipe position
    class EditPosition(
        val position: Position, onEvent: (MainEvent) -> Unit,
    ) : DialogData(onEvent)

    class ChooseDay @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, onEventVM: (Event) -> Unit,
    ) : DialogData(onEventVM){
        @OptIn(ExperimentalMaterial3Api::class)
        val navigateToNewWeek = {
            val date = state.selectedDateMillis
            if (date != null) {
                onEventVM(MenuEvent.ChangeWeek(date.dateToLocalDate()))
            }
            close()
        }
    }

    class SelectedToShopList(onEventVM: (MainEvent) -> Unit) : DialogData(onEventVM)
    class ToShopList(val recipe: Position.PositionRecipeView, onEventVM: (MainEvent) -> Unit) :
        DialogData(onEventVM)


    //todo wrong module, please move*******************************************************************************************
    class SelectIngredients @OptIn(ExperimentalMaterial3Api::class) constructor(
        val sheetState: SheetState,
        val onEventVM: (MainEvent) -> Unit,
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
            //todo
            //onEventVM(MainEvent.OpenDialog(createDialogData))
        }
    }

    class FindIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val sheetState: SheetState,
        val onEventVM: (MainEvent) -> Unit,
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
            //todo
            //onEventVM(MainEvent.OpenDialog(createDialogData))
        }
    }

    class CreateIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        startValue: String,
        val sheetState: SheetState,
        val onEventVM: (MainEvent) -> Unit,
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

    class SelectCategoryIngredient(onEventVM: (MainEvent) -> Unit) : DialogData(onEventVM) {
        val search = mutableStateOf<String>("")
    }

    class CreateCategoryIngredient @OptIn(ExperimentalMaterial3Api::class) constructor(
        val sheetState: SheetState, onEventVM: (MainEvent) -> Unit,
    ) : DialogData(onEventVM) {
        val text = mutableStateOf<String>("")
    }

    class AddTag(val startName: String, onEventVM: (MainEvent) -> Unit) : DialogData(onEventVM)
    class AddIngredient(val startName: String, onEventVM: (MainEvent) -> Unit) : DialogData(onEventVM)
}
