package week.on.a.plate.menuScreen.view.recipeBlock

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.SelectionInDayData
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.menuScreen.view.uiTools.TitleMenu
import week.on.a.plate.menuScreen.view.uiTools.TitleMenuSmall

@Composable
fun BlockSelection(
    selection: SelectionInDayData, editing: MutableState<Boolean>,
    actionAdd: () -> Unit,
    actionNavToFullRecipe: (RecipeView) -> Unit,
    checkAction: (id: Long) -> Unit,
    switchEditMode: () -> Unit,
    actionEdit: (id: Long) -> Unit,
    actionRecipeToNextStep: (id: Long) -> Unit,
    getCheckState: (id: Long) -> State<Boolean>
) {
    TitleMenu(selection.category) {
        actionAdd()
    }
    Spacer(Modifier.height(10.dp))
    for (rec in selection.recipes) {
        RecipePosition(rec, editing,
            actionNavToFullRecipe = actionNavToFullRecipe,
            checkAction = checkAction,
            switchEditMode = switchEditMode,
            actionEdit = actionEdit,
            actionRecipeToNextStep = actionRecipeToNextStep,
            getCheckState = getCheckState
        )
    }
    Spacer(Modifier.height(10.dp))
}


@Composable
fun BlockSelectionSmall(
    selection: SelectionInDayData,
    editing: MutableState<Boolean>,
    actionAdd: () -> Unit,
    actionNavToFullRecipe: (RecipeView) -> Unit,
    checkAction: ( id: Long) -> Unit,
    switchEditMode: () -> Unit,
    actionEdit: (id: Long) -> Unit,
    actionRecipeToNextStep: (id: Long) -> Unit,
    getCheckState: (id: Long) -> State<Boolean>
) {
    TitleMenuSmall(selection.category) {
        actionAdd()
    }
    Spacer(Modifier.height(10.dp))
    for (rec in selection.recipes) {
        RecipePosition(rec, editing,
            actionNavToFullRecipe = actionNavToFullRecipe,
            checkAction = checkAction,
            switchEditMode = switchEditMode,
            actionEdit = actionEdit,
            actionRecipeToNextStep = actionRecipeToNextStep,
            getCheckState = getCheckState
        )
    }
}