package week.on.a.plate.dialogEditPositionIngredient.state

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.week.Position

class EditPositionIngredientUIState (
    val positionIngredientView: Position.PositionIngredientView?,
){
    val ingredientState: MutableState<IngredientView?> = mutableStateOf(positionIngredientView?.ingredient?.ingredientView)
    val description: MutableState<String> = mutableStateOf(positionIngredientView?.ingredient?.description?:"")
    val count: MutableIntState = mutableIntStateOf(positionIngredientView?.ingredient?.count?:0)
    val show: MutableState<Boolean> = mutableStateOf(true)
}


