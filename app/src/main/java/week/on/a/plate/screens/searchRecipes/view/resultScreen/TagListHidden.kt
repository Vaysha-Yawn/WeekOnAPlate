package week.on.a.plate.screens.searchRecipes.view.resultScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TagSmall
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView

@Composable
fun TagListHidden(tags: List<RecipeTagView>, ingredients: List<IngredientView>) {
    Column {
        if (tags.isNotEmpty()) {
            TagsFlowRowWithHidden(sizeList = tags.size) { index ->
                TagSmall(tag = tags[index])
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        if (ingredients.isNotEmpty()) {
            TagsFlowRowWithHidden(ingredients.size) { index ->
                TagSmall(ingredientView = ingredients[index])
            }
        }
    }
}


@Composable
fun TagList(tags: List<RecipeTagView>, ingredients: List<IngredientView>) {
    Column {
        if (tags.isNotEmpty()) {
            TagsFlowRow(sizeList = tags.size) { index ->
                TagSmall(tag = tags[index])
                Spacer(modifier = Modifier.size(6.dp))
            }
        }
        Spacer(modifier = Modifier.size(6.dp))
        if (ingredients.isNotEmpty()) {
            TagsFlowRow(ingredients.size) { index ->
                TagSmall(ingredientView = ingredients[index])
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsFlowRow(sizeList: Int, content: @Composable (Int) -> Unit) {
    FlowRow(Modifier.fillMaxWidth()) {
        for (i in 0 until sizeList) {
            content(i)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsFlowRowWithHidden(sizeList: Int, content: @Composable (Int) -> Unit) {
    val showMore = remember {
        mutableStateOf(false)
    }
    FlowRow(Modifier.fillMaxWidth()) {
        if (sizeList > 2) {
            for (i in 0 until 2) {
                content(i)
            }
            if (showMore.value) {
                for (i in 2 until sizeList) {
                    content(i)
                }
                TextSmall(
                    text = "—", modifier = Modifier
                        .clickable {
                            showMore.value = false
                        }
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                        .padding(vertical = 3.dp)
                        .padding(horizontal = 12.dp)
                )
            } else {
                TextSmall(
                    text = "+", modifier = Modifier
                        .clickable {
                            showMore.value = true
                        }
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                        .padding(vertical = 3.dp)
                        .padding(horizontal = 12.dp)
                )
            }
        } else {
            for (i in 0 until sizeList) {
                content(i)
            }
        }
    }
}