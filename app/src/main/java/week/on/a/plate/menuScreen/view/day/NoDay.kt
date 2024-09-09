package week.on.a.plate.menuScreen.view.day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.logic.MenuEvent
import java.time.LocalDate

@Composable
fun NoDay(data: LocalDate, onEvent:(MenuEvent)->Unit){
    Column(Modifier.fillMaxSize().padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextTitle(text = "Нет данных")
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton("Добавить день") {
            onEvent(MenuEvent.AddEmptyDay(data))
        }
    }
}