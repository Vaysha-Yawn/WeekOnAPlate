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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.screens.filters.view.clickNoRipple
import week.on.a.plate.screens.settings.event.SettingsEvent
import week.on.a.plate.screens.settings.logic.SettingsViewModel
import week.on.a.plate.screens.settings.state.SettingsUIState

data class SettingItem(val name: String, val imgRes: Int?, val event: SettingsEvent)

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
    val context = LocalContext.current

    val listSettingsItems = remember {
        listOf(
            SettingItem("Изменить тему", null, SettingsEvent.Theme(context)),
            //SettingItem("Обучение", null, SettingsEvent.Tutorial(context)),
            SettingItem(
                "Изменить стандартное количество порций",
                null,
                SettingsEvent.SetStdPortionsCount(context)
            ),
            SettingItem("Редактировать приёмы пищи", null, SettingsEvent.SetMenuSelections(context)),
            //SettingItem("Импортировать рецепты", null, SettingsEvent.Import(context)),
            //SettingItem("Экспортировать рецепты", null, SettingsEvent.Export(context)),
            //SettingItem("Включить большие шрифты", null, SettingsEvent.BigType(context)),
            //SettingItem("Аккаунт", null, SettingsEvent.Profile(context)),
            SettingItem("Оценить приложение", null, SettingsEvent.RateApp(context)),
            //SettingItem("Премиум", null, SettingsEvent.Premium(context)),
            SettingItem(
                "Политика конфиденциальности и условия использования",
                null,
                SettingsEvent.PrivacyPolicy(context)
            ),
        )
    }


    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {

        TextTitleLarge(stringResource(R.string.settings))
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            itemsIndexed(listSettingsItems){ind, item->
                Spacer(Modifier.height(36.dp))
                ButtonSettings(
                    item.imgRes?:R.drawable.settings,
                    text = item.name,
                ) {
                    onEvent(item.event)
                }
            }
        }
    }
}


@Composable
fun ButtonSettings(imgRec: Int, text: String, event: () -> Unit) {
    Row(modifier = Modifier
        .clickNoRipple (event), verticalAlignment = Alignment.Top) {
        TextBody(text = text, modifier = Modifier.weight(1f), textAlign = TextAlign.Start)
        Image(
            painter = painterResource(id = R.drawable.forward),
            contentDescription = text,
            modifier = Modifier
                .size(24.dp)
        )
    }
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