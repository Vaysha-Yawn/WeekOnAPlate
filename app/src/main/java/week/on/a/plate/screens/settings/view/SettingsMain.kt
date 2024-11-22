package week.on.a.plate.screens.settings.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.screens.settings.event.SettingsEvent
import week.on.a.plate.screens.settings.logic.SettingsViewModel
import week.on.a.plate.screens.settings.state.SettingsUIState

data class SettingItem(val name: String, val imgRes: Int?, val event: SettingsEvent)

val listSettingsItems = listOf(
    SettingItem("Изменить тему", null, SettingsEvent.Done),
    SettingItem("Включить большие шрифты", null, SettingsEvent.Done),
    SettingItem("Обучение", null, SettingsEvent.Done),
    SettingItem("Изменить стандартное количество порций", null, SettingsEvent.Done),
    SettingItem("Редактировать приёмы пищи", null, SettingsEvent.Done),
    SettingItem("Импортировать рецепты", null, SettingsEvent.Done),
    SettingItem("Экспортировать рецепты", null, SettingsEvent.Done),
    SettingItem("Аккаунт", null, SettingsEvent.Done),
    SettingItem("Оценить приложение", null, SettingsEvent.Done),
    SettingItem("Премимум", null, SettingsEvent.Done),
    SettingItem("Политика конфиденциальности и условия использования", null, SettingsEvent.Done),
)


@Composable
fun SettingsStart(vm: SettingsViewModel) {
    val state = vm.state
    val onEvent = { event: Event ->
        vm.onEvent(event)
    }
    SettingsContent(state, onEvent)
}

@Composable
fun SettingsContent(state: SettingsUIState, onEvent: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {

        TextDisplayItalic(stringResource(R.string.settings))
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(12.dp))

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            itemsIndexed(listSettingsItems){ind, item->
                ButtonSettings(
                    item.imgRes?:R.drawable.settings,
                    text = item.name,
                ) {
                    onEvent(item.event)
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}


@Composable
fun ButtonSettings(imgRec: Int, text: String, event: () -> Unit) {
    Row(modifier = Modifier
        .padding(vertical = 10.dp)
        .clickable { event() }) {
        Image(
            painter = painterResource(id = imgRec),
            contentDescription = text,
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(Modifier.size(12.dp))
        TextBody(text = text, modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.forward),
            contentDescription = text,
            modifier = Modifier
                .size(24.dp)
        )
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    WeekOnAPlateTheme {
        SettingsContent(
            SettingsUIState()
        ) {}
    }
}