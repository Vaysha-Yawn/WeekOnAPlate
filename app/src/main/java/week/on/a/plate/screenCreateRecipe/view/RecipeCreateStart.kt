package week.on.a.plate.screenCreateRecipe.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screenCreateRecipe.event.RecipeCreateEvent
import week.on.a.plate.screenCreateRecipe.logic.RecipeCreateViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeCreateStart(viewModel: RecipeCreateViewModel) {
    val onEvent = { event: RecipeCreateEvent ->
        viewModel.onEvent(event)
    }
    val state = rememberLazyListState()
    Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
        TopBarRecipeCreate(viewModel.state, onEvent)
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                SourceRecipeEdit(viewModel.state, onEvent)
            }
            stickyHeader {
                TabCreateRecipe(viewModel.state, onEvent)
                if (viewModel.state.activeTabIndex.intValue == 1) {
                    RowWebActions(viewModel.state)
                }
            }
            if (viewModel.state.activeTabIndex.intValue == 1) {
                item {
                    WebPageCreateRecipe(viewModel.state) { event ->
                        viewModel.onEvent(event)
                    }
                }
            } else {
                item {
                    Column(Modifier.padding(24.dp)) {
                        PhotoRecipeEdit(viewModel.state, onEvent)
                        Spacer(modifier = Modifier.height(24.dp))
                        NameRecipeEdit(viewModel.state, onEvent)
                        Spacer(modifier = Modifier.height(24.dp))
                        DescriptionRecipeEdit(viewModel.state, onEvent)
                        Spacer(modifier = Modifier.height(24.dp))
                        TimeCookRecipeEdit(viewModel.state, onEvent)
                        Spacer(modifier = Modifier.height(24.dp))
                        PortionsRecipeEdit(viewModel.state, onEvent)
                        Spacer(modifier = Modifier.height(24.dp))
                        TagsRecipeEdit(viewModel.state, onEvent)
                    }
                }
                item {
                    Column(Modifier.padding(24.dp)) {
                        TextTitle(text = "Ингредиенты")
                        Spacer(modifier = Modifier.size(12.dp))
                        SubText(
                            text = "Нажмите чтобы редактировать, удерживаете чтобы удалить",
                            textAlign = TextAlign.Start
                        )
                    }
                }
                items(viewModel.state.ingredients.value.size) {
                    if (viewModel.state.ingredients.value.isNotEmpty()) {
                        IngredientRecipeEdit(
                            viewModel.state.ingredients.value[it],
                            viewModel.state,
                            onEvent
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                item {
                    Column(
                        Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        CommonButton(text = "Добавить ингредиент") {
                            onEvent(RecipeCreateEvent.AddIngredient)
                        }
                        Spacer(modifier = Modifier.size(24.dp))
                        CommonButton(text = "Добавить несколько ингредиентов") {
                            onEvent(RecipeCreateEvent.AddManyIngredients)
                        }
                    }
                }
                item {
                    Column(Modifier.padding(top = 36.dp, bottom = 12.dp, start = 24.dp)) {
                        TextTitle(text = "Пошаговый рецепт")
                    }
                }
                items(viewModel.state.steps.value.size) {
                    Column(Modifier.padding(24.dp)) {
                        StepRecipeEdit(
                            it,
                            viewModel.state.steps.value[it],
                            viewModel.state,
                            onEvent
                        )
                    }
                }
                item {
                    Column(Modifier.padding(24.dp)) {
                        CommonButton(text = "Добавить шаг") {
                            onEvent(RecipeCreateEvent.AddStep)
                        }
                    }
                    Spacer(modifier = Modifier.height(400.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeCreateStart() {
    WeekOnAPlateTheme {
        RecipeCreateStart(RecipeCreateViewModel().apply {
            this.setStateByOldRecipe(recipeTom)
        })
    }
}