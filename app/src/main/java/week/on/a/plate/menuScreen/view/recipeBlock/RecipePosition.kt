package week.on.a.plate.menuScreen.view.recipeBlock

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.shortRecipe
import week.on.a.plate.core.data.recipe.RecipeStateView
import week.on.a.plate.core.data.week.RecipeInMenuView
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.TextInAppColored
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.EditButton
import week.on.a.plate.ui.theme.ColorPanel
import week.on.a.plate.ui.theme.ColorSecond
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipePosition(
    recipe: RecipeInMenuView,
    editing: MutableState<Boolean>,
    actionNavToFullRecipe: (RecipeShortView) -> Unit,
    checkAction: (id: Long) -> Unit,
    switchEditMode: () -> Unit,
    actionEdit: (id: Long) -> Unit,
    actionRecipeToNextStep: (id: Long) -> Unit,
    getCheckState: (id: Long) -> State<Boolean>
) {
    val density = LocalDensity.current
    val anchors = DraggableAnchors {
        false at with(density) { 0.dp.toPx() }
        true at with(density) { 80.dp.toPx() }
    }

    val interactionSource = remember { MutableInteractionSource() }
    val state = remember {
        AnchoredDraggableState(
            false, anchors = anchors,
            positionalThreshold = { distance: Float -> distance * 0.9f },
            velocityThreshold = { with(density) { 1.dp.toPx() } },
            animationSpec = tween()
        )
    }

    val backgroundColor = animateColorAsState(
        if (state.offset.dp >= 180.dp) ColorSecond else ColorPanel
    )

    LaunchedEffect(state.currentValue) {
        when (state.currentValue) {
            false -> {}
            true -> {
                actionRecipeToNextStep(recipe.id)
                state.animateTo(false)
            }
        }
    }

    Card(
        Modifier
            .padding(bottom = 10.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        shape = RectangleShape
    ) {
        Box() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .anchoredDraggable(
                        state = state,
                        reverseDirection = false,
                        orientation = Orientation.Horizontal,
                        enabled = true,
                        interactionSource = interactionSource
                    )
                    .offset(x = state.offset.dp)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = switchEditMode,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (editing.value) {
                    CheckButton(getCheckState(recipe.id)) {
                        checkAction(recipe.id)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Column(
                    Modifier.weight(3f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(Modifier.padding(bottom = 3.dp)) {
                        TextInAppColored(
                            recipe.state.names, colorBackground = ColorSecond,
                            modifier = Modifier
                                .padding(end = 10.dp)
                        )
                        TextInApp(
                            "${recipe.portionsCount} Порции"
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextInApp(
                            recipe.recipe.name,
                            maxLines = 1,
                            modifier = Modifier.combinedClickable(
                                onClick = { actionNavToFullRecipe(recipe.recipe) },
                                onLongClick = switchEditMode
                            ),
                            textStyle = Typography.bodyMedium
                        )
                    }
                }
                EditButton { actionEdit(recipe.id) }
            }
            if (recipe.state.nextStep!=""){
                TextInApp(
                    text = recipe.state.nextStep,
                    modifier = Modifier
                        .width(200.dp)
                        .offset(x = state.offset.dp - 220.dp)
                        .background(backgroundColor.value)
                        .padding(vertical = 15.dp, horizontal = 20.dp), textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipePosition() {
    WeekOnAPlateTheme {
        val editing = remember {
            mutableStateOf(false)
        }
        Column {
            RecipePosition(
                RecipeInMenuView(
                    0,
                    RecipeStateView.Done,
                    week.on.a.plate.core.data.example.shortRecipe,
                    1,
                ), editing, {}, { id -> }, {}, {}, {}, { i -> editing })
            RecipePosition(
                RecipeInMenuView(
                    0,
                    RecipeStateView.Eated,
                    shortRecipe,
                    2,
                ), editing, {}, { id -> }, {}, {}, {}, { i -> editing })
            RecipePosition(
                RecipeInMenuView(
                    0,
                    RecipeStateView.Created,
                    shortRecipe,
                    3,
                ), editing, {}, { id -> }, {}, {}, {}, { i -> editing })
        }

    }
}
