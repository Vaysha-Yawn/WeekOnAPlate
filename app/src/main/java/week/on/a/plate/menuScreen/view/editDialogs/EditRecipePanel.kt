package week.on.a.plate.menuScreen.view.editDialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.buttons.ButtonText
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun EditRecipePanel(){
    Column(modifier = Modifier.padding(20.dp)) {
        ButtonText(
            "Изменить состояние", modifier = Modifier.fillMaxWidth().clickable {
                //
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonText(
            "Дублировать в ...", modifier = Modifier.fillMaxWidth().clickable {
                //
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonText(
            "Удалить", modifier = Modifier.fillMaxWidth().clickable {
                //
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonText(
            "Поменять рецепт", modifier = Modifier.fillMaxWidth().clickable {
                //
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonText(
            "Изменить кол-во порций", modifier = Modifier.fillMaxWidth().clickable {
                //
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditRecipePanel() {
    WeekOnAPlateTheme {
        EditRecipePanel()
    }
}