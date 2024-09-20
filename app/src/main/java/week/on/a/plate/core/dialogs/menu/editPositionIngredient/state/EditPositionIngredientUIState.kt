package week.on.a.plate.core.dialogs.menu.editPositionIngredient.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.week.Position

class EditPositionIngredientUIState @OptIn(ExperimentalMaterial3Api::class) constructor(
    positionIngredientView: Position.PositionIngredientView?,
    val sheetState: SheetState
){
    val ingredientState: MutableState<IngredientView?> = mutableStateOf(positionIngredientView?.ingredient?.ingredientView)
    val description: MutableState<String> = mutableStateOf(positionIngredientView?.ingredient?.description?:"")
    val count: MutableDoubleState = mutableDoubleStateOf(positionIngredientView?.ingredient?.count?:0.0)
    val show: MutableState<Boolean> = mutableStateOf(true)
}


