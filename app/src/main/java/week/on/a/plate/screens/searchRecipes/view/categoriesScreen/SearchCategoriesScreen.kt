package week.on.a.plate.screens.searchRecipes.view.categoriesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.screens.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.searchRecipes.state.SearchUIState

@Composable
fun SearchCategoriesScreen(stateUI: SearchUIState, onEvent: (SearchScreenEvent) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        item {
            TextTitle(text = "Особое")
            Spacer(modifier = Modifier.size(12.dp))
            LazyRow {
                item {
                    CardAll(onEvent)
                    CardFavorite(onEvent)
                    CardRandom(onEvent)
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
        }
        items(stateUI.allTagsCategories.value.size) {
            CategorySelection(stateUI.allTagsCategories.value[it], onEvent)
        }
    }
}

@Composable
fun CategorySelection(tagCategoryView: TagCategoryView, onEvent: (SearchScreenEvent) -> Unit) {
    TextTitle(text = tagCategoryView.name)
    Spacer(modifier = Modifier.height(12.dp))
    LazyRow {
        items(tagCategoryView.tags.size) {
            CardTag(tagCategoryView.tags[it], onEvent)
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun CardAbstractCategoryRecipe(name: String, click: () -> Unit) {
    Card(
        Modifier
            .clickable {
                click()
            }
            .padding(end = 24.dp)
            .width(130.dp)
            .height(130.dp),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.outline)
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextBody(text = name)
        }
    }
}

@Composable
fun CardAll(onEvent: (SearchScreenEvent) -> Unit) {
    CardAbstractCategoryRecipe("Все рецепты"){
        onEvent(SearchScreenEvent.SearchAll)
    }
}

@Composable
fun CardRandom(onEvent: (SearchScreenEvent) -> Unit) {
    CardAbstractCategoryRecipe("Случайные"){
        onEvent(SearchScreenEvent.SearchRandom)
    }
}

@Composable
fun CardFavorite(onEvent: (SearchScreenEvent) -> Unit) {
    CardAbstractCategoryRecipe("Избранное"){
        onEvent(SearchScreenEvent.SearchFavorite)
    }
}

@Composable
fun CardTag(recipeTagView: RecipeTagView, onEvent: (SearchScreenEvent) -> Unit) {
    Card(
        Modifier
            .clickable {
                onEvent(SearchScreenEvent.SelectTag(recipeTagView))
                onEvent(SearchScreenEvent.Search)
            }
            .padding(end = 24.dp)
            .width(130.dp)
            .height(130.dp),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.outline)
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextBody(text = recipeTagView.tagName)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchCategoriesScreen() {
    WeekOnAPlateTheme {
        SearchCategoriesScreen(SearchUIState().apply {
            allTagsCategories = mutableStateOf(tags)
        }) {}
    }
}
