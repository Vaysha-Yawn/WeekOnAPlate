package week.on.a.plate.menuScreen.view.dialogs.editDialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionRecipeExample
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.ButtonText
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.ui.theme.ColorBluePanel
import week.on.a.plate.ui.theme.ColorTransparent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun EditRecipePanel(recipe:Position.PositionRecipeView, onEvent: (MenuEvent) -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        ButtonRow(
            R.drawable.add_shopping_cart,
            "Добавить в покупки",
            MenuEvent.AddRecipeToShoppingList(recipe),
            onEvent
        )

        ButtonRow(
            R.drawable.add,
            "Дублировать",
            MenuEvent.Double(recipe),
            onEvent
        )

        ButtonRow(
            R.drawable.delete,
            "Удалить",
            MenuEvent.Delete(recipe),
            onEvent
        )

        ButtonRow(
            R.drawable.find_replace,
            "Поменять рецепт",
            MenuEvent.FindReplace(recipe),
            onEvent
        )

        ButtonRow(
            R.drawable.numbers,
            "Изменить кол-во порций",
            MenuEvent.ChangeCount(recipe),
            onEvent
        )

        ButtonRow(
            R.drawable.back_key,
            "Переместить",
            MenuEvent.Move(recipe),
            onEvent
        )
    }
}

@Composable
fun ButtonRow(imgRec: Int, text: String, event: MenuEvent, onEvent: (MenuEvent) -> Unit) {
    Row(modifier = Modifier
        .padding(vertical = 10.dp)
        .clickable { onEvent(event) }) {
        Image(
            painter = painterResource(id = imgRec),
            contentDescription = text,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp)
        )
        TextBody(text = text)
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
}

@Preview(showBackground = true)
@Composable
fun PreviewEditRecipePanel() {
    WeekOnAPlateTheme {
        EditRecipePanel(positionRecipeExample, {})
    }
}