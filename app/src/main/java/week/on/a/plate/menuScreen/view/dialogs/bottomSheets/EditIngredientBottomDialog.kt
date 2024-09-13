package week.on.a.plate.menuScreen.view.dialogs.bottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionIngredientExample
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.uitools.EditNumberLine
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.uitools.ingredientCard.CardIngredient
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.NavFromMenuData
import week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen.dateToLocalDate
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun AddIngredientBottomDialogContent(
    data: DialogMenuData.AddIngredient, onEvent: (MenuEvent) -> Unit
) {
    EditOrAddIngredientBottomDialogContent(
        "Добавить",
        data.ingredientState,
        data.text,
        data.count,
        onEvent
    ) {
        onEvent(MenuEvent.ActionDBMenu(ActionDBData.AddIngredientDB(data)))
        onEvent(MenuEvent.CloseDialog)
    }
}


@Composable
fun EditIngredientBottomDialogContent(
    data: DialogMenuData.EditIngredient, onEvent: (MenuEvent) -> Unit
) {
    EditOrAddIngredientBottomDialogContent(
        "Подтвердить",
        data.ingredientState,
        data.text,
        data.count,
        onEvent
    ) {
        onEvent(MenuEvent.ActionDBMenu(ActionDBData.EditIngredientDB(data)))
        onEvent(MenuEvent.CloseDialog)
    }
}


@Composable
fun EditOrAddIngredientBottomDialogContent(
    doneText: String,
    ingredientState: MutableState<IngredientView?>,
    description: MutableState<String>,
    count: MutableDoubleState,
    onEvent: (MenuEvent) -> Unit,
    done: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                if (snackbarHostState.currentSnackbarData != null) {
                    Snackbar(
                        snackbarData = snackbarHostState.currentSnackbarData!!,
                        modifier = Modifier.padding(bottom = 80.dp),
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    ) { padding ->
        padding

        Column(modifier = Modifier.padding(vertical = 24.dp)) {
            TextBody(text = "Ингредиент", modifier = Modifier.padding(horizontal = 48.dp))
            Spacer(modifier = Modifier.height(12.dp))
            if (ingredientState.value != null) {
                CardIngredient(ingredient = ingredientState.value!!, {
                    Image(
                        painter = painterResource(id = R.drawable.find_replace),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }) {
                    onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.NavToChooseIngredient))
                }
            } else {
                CommonButton("Указать ингредиент", R.drawable.search) {
                    onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.NavToChooseIngredient))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            EditTextLine(
                description,
                "Пояснение",
                "Кусочками...", modifier = Modifier.padding(horizontal = 24.dp)
            ) { value ->
                description.value = value
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                EditNumberLine(
                    count,
                    "Количество, ${ingredientState.value?.measure ?: ""}",
                    "0.0",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) { newnum ->
                    count.doubleValue = newnum
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            DoneButton(text = doneText, modifier = Modifier.padding(horizontal = 24.dp)) {
                if (ingredientState.value!=null) {
                    done()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Пожалуйста, выберите ингредиент"
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewEditIngredientBottomDialog() {
    WeekOnAPlateTheme {
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        EditIngredientBottomDialogContent(
            DialogMenuData.EditIngredient(
                positionIngredientExample,
                stateBottom
            )
        ) {}
    }
}