package week.on.a.plate.screens.createRecipe.view.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

fun LazyListScope.RecipeEditPage(
    state: RecipeCreateUIState,
    onEvent: (RecipeCreateEvent) -> Unit
) {
    item {
        Column(Modifier.padding(24.dp)) {
            PhotoRecipeEdit(state, onEvent)
            Spacer(modifier = Modifier.height(24.dp))
            DescriptionRecipeEdit(state, onEvent)
            Spacer(modifier = Modifier.height(24.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                RecipeDurationEdit(Modifier.weight(1f), state, onEvent)
                PortionsRecipeEdit(Modifier.weight(1f), state, onEvent)
            }

            Spacer(modifier = Modifier.height(24.dp))
            TagsRecipeEdit(state, onEvent)
        }
    }
    item {
        Column(Modifier.padding(24.dp)) {
            TextTitle(text = stringResource(R.string.ingredients))
        }
    }
    items(state.ingredients.value.size) {
        if (state.ingredients.value.isNotEmpty()) {
            if (state.ingredients.value.isNotEmpty()) {
                IngredientRecipeEdit(
                    state.ingredients.value[it],
                    state,
                    onEvent
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
    item {
        Column(
            Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            CommonButton(text = stringResource(R.string.edit_ingredients)) {
                onEvent(RecipeCreateEvent.AddManyIngredients)
            }
        }
    }
    item {
        Column(Modifier.padding(top = 36.dp, bottom = 12.dp, start = 24.dp)) {
            TextTitle(text = stringResource(R.string.step_by_step_recipe))
        }
    }
    items(state.steps.value.size) {
        Column(Modifier.padding(horizontal = 24.dp)) {
            StepRecipeEdit(
                it,
                state.steps.value[it],
                state,
                onEvent
            )
            Spacer(Modifier.height(48.dp))
        }
    }
    item {
        Column(Modifier.padding(24.dp)) {
            CommonButton(text = stringResource(R.string.add_step)) {
                onEvent(RecipeCreateEvent.AddStep)
            }
        }
        Spacer(modifier = Modifier.height(300.dp))
    }
}