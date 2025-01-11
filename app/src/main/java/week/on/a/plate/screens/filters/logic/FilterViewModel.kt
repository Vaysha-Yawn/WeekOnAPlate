package week.on.a.plate.screens.filters.logic

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.startCategoryName
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.dialogs.editOrCreateTag.logic.AddTagViewModel
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogs.editOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.filters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode
import week.on.a.plate.screens.filters.state.FilterResult
import week.on.a.plate.screens.filters.state.FilterUIState
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val ingredientCategoryRepository: IngredientCategoryRepository,
    private val recipeTagCategoryRepository: RecipeTagCategoryRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeTagRepository: RecipeTagRepository,
    private val voiceSearch: VoiceSearchUseCase
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = FilterUIState()
    lateinit var allIngredients: StateFlow<List<IngredientCategoryView>>
    lateinit var allTags: StateFlow<List<TagCategoryView>>
    private var resultFlow: MutableStateFlow<FilterResult?>? = null
    private var resultFlowCategory: MutableStateFlow<FilterResult?>? = null
    var isForCategory = false

    init {
        viewModelScope.launch {
            allIngredients = ingredientCategoryRepository.getAllIngredientsByCategoriesForFilters()
                .stateIn(viewModelScope)
            allTags = recipeTagCategoryRepository.getAllTagsByCategoriesForFilters()
                .stateIn(viewModelScope)
        }
    }

    suspend fun launchAndGet(
        mode: FilterMode, enum: FilterEnum,
        lastFilters: Pair<List<RecipeTagView>, List<IngredientView>>?, isForCategory: Boolean,
        use: suspend (FilterResult) -> Unit
    ) {
        state.selectedTags.value = lastFilters?.first ?: listOf()
        state.selectedIngredients.value = lastFilters?.second ?: listOf()
        state.filterMode.value = mode
        state.filterEnum.value = enum

        this.isForCategory = isForCategory
        if (isForCategory) {
            resultFlowCategory = MutableStateFlow(null)
            resultFlowCategory!!.collect { value ->
                if (value != null) {
                    use(value)
                }
            }
        } else {
            resultFlow = MutableStateFlow(null)
            resultFlow!!.collect { value ->
                if (value != null) {
                    use(value)
                }
            }
        }
    }

    fun onEvent(event: FilterEvent) {
        when (event) {
            FilterEvent.Back -> close()
            FilterEvent.Done -> done()
            is FilterEvent.SearchFilter -> search(event.text)
            FilterEvent.SelectedFilters -> openSelectedFilters()
            is FilterEvent.VoiceSearchFilters -> voiceSearch(
                event.context,
                mainViewModel,
                ::onEvent,
                state.searchText,
                viewModelScope,
                state.filterEnum.value,
                ::searchTags,
                ::searchIngredients,
                ::searchTagsCategories,
                ::searchIngredientCategories
            )

            FilterEvent.ClearSearch -> clear()

            is FilterEvent.CreateIngredient -> toCreateIngredient(event.context)
            is FilterEvent.CreateTag -> toCreateTag(event.context)
            is FilterEvent.CreateIngredientCategory -> toCreateIngredientCategory()
            is FilterEvent.CreateTagCategory -> toCreateTagCategory()

            is FilterEvent.SelectIngredient -> selectIngredient(event.ingredient)
            is FilterEvent.SelectTag -> selectTag(event.tag)
            is FilterEvent.SelectIngredientCategory -> selectIngredientCategory(event.ingredientCategoryView)
            is FilterEvent.SelectTagCategory -> selectTagCategory(event.tagCategoryView)

            is FilterEvent.EditOrDeleteIngredient -> editOrDelete(
                delete = { deleteIngredient(event.ingredient, event.context) },
                edit = { editIngredient(event.context, event.ingredient) })

            is FilterEvent.EditOrDeleteTag -> editOrDelete(
                delete = { deleteTag(event.tag, event.context) },
                edit = { editTag(event.tag) })

            is FilterEvent.EditOrDeleteIngredientCategory -> {
                if (event.ingredientCategory.name == event.context.getString(startCategoryName)) return
                editOrDelete(
                    delete = { deleteIngredientCategory(event.ingredientCategory) },
                    edit = { editIngredientCategory(event.ingredientCategory) })
            }

            is FilterEvent.EditOrDeleteTagCategory -> {
                if (event.tagCategory.name == event.context.getString(startCategoryName)) return
                editOrDelete(
                    delete = { deleteTagCategory(event.tagCategory) },
                    edit = { editTagCategory(event.tagCategory) })
            }

            is FilterEvent.CreateActive -> {
                createFilterElement(event.context)
            }
        }
    }

    private fun createFilterElement(context: Context) {
        when (state.filterEnum.value) {
            FilterEnum.Ingredient -> onEvent(FilterEvent.CreateIngredient(context))
            FilterEnum.Tag -> onEvent(FilterEvent.CreateTag(context))
            FilterEnum.CategoryTag -> onEvent(FilterEvent.CreateTagCategory(context))
            FilterEnum.CategoryIngredient -> onEvent(FilterEvent.CreateIngredientCategory(context))
            FilterEnum.IngredientAndTag -> if (state.activeFilterTabIndex.intValue == 0) {
                onEvent(FilterEvent.CreateTag(context))
            } else {
                onEvent(FilterEvent.CreateIngredient(context))
            }
        }
    }

    private fun editOrDelete(delete: () -> Unit, edit: () -> Unit) {
        EditOrDeleteViewModel.launch(mainViewModel) { event ->
            when (event) {
                EditOrDeleteEvent.Close -> {}
                EditOrDeleteEvent.Delete -> delete()
                EditOrDeleteEvent.Edit -> edit()
            }
        }
    }

    // DELETE

    private fun deleteIngredient(ingredient: IngredientView, context: Context) {
        viewModelScope.launch {
            val vmDel = mainViewModel.deleteApplyViewModel
            val mes = context.getString(R.string.delete_ingredient)
            mainViewModel.nav.navigate(DeleteApplyDestination)
            vmDel.launchAndGet(context, message = mes) { event ->
                if (event == DeleteApplyEvent.Apply) {
                    ingredientRepository.delete(ingredient.ingredientId)
                }
            }
        }
    }

    private fun deleteTag(tag: RecipeTagView, context: Context) {
        viewModelScope.launch {
            val vmDel = mainViewModel.deleteApplyViewModel
            val mes = context.getString(R.string.delete_tag)
            mainViewModel.nav.navigate(DeleteApplyDestination)
            vmDel.launchAndGet(context, message = mes) { event ->
                if (event == DeleteApplyEvent.Apply) {
                    recipeTagRepository.delete(tag.id)
                }
            }
        }
    }

    private fun deleteTagCategory(oldCategory: TagCategoryView) {
        viewModelScope.launch {
            recipeTagCategoryRepository.delete(oldCategory.id)
        }
    }

    private fun deleteIngredientCategory(oldCategory: IngredientCategoryView) {
        viewModelScope.launch {
            ingredientCategoryRepository.delete(oldCategory.id)
        }
    }

    /// EDIT

    private fun editIngredientCategory(oldIngredientCat: IngredientCategoryView) {
        EditOneStringViewModel.launch(
            mainViewModel, EditOneStringUIState(
                oldIngredientCat.name,
                R.string.edit_category_name,
                R.string.enter_category_name,
            )
        ) { newName ->
            viewModelScope.launch {
                ingredientCategoryRepository.updateName(newName, oldIngredientCat.id)
            }
        }
    }

    private fun editTagCategory(oldTagCat: TagCategoryView) {
        EditOneStringViewModel.launch(
            mainViewModel, EditOneStringUIState(
                oldTagCat.name,
                R.string.edit_category_name,
                R.string.enter_category_name,
            )
        ) { newName ->
            viewModelScope.launch {
                recipeTagCategoryRepository.updateName(newName, oldTagCat.id)
            }
        }
    }

    private fun editTag(tag: RecipeTagView) {
        val oldCategory = allTags.value.find { it.tags.contains(tag) }
        AddTagViewModel.launch(
            tag.tagName, oldCategory, oldCategory!!, mainViewModel
        ) { newNameAndCategory ->
            viewModelScope.launch {
                editTagDB(tag, newNameAndCategory.first, newNameAndCategory.second)
            }
        }
    }

    private suspend fun editTagDB(
        oldTag: RecipeTagView,
        newName: String,
        newCategory: TagCategoryView
    ) {
        recipeTagRepository.update(oldTag.id, newName, newCategory.id)
    }

    private fun editIngredient(context: Context, ingredient: IngredientView) {
        val oldCategory = allIngredients.value.find { it.ingredientViews.contains(ingredient) }!!
        AddIngredientViewModel.launch(
            context,
            ingredient,
            oldCategory,
            oldCategory,
            mainViewModel
        ) { newIngredientAndCategory ->
            viewModelScope.launch {
                editIngredientDB(
                    ingredient,
                    newIngredientAndCategory.first,
                    newIngredientAndCategory.second
                )
                mainViewModel.filterViewModel.onEvent(FilterEvent.SearchFilter())
            }
        }
    }

    private suspend fun editIngredientDB(
        ingredientOld: IngredientView,
        ingredientNew: IngredientView,
        newCategory: IngredientCategoryView
    ) {
        ingredientRepository.update(
            ingredientOld.ingredientId,
            newCategory.id,
            ingredientNew.img,
            ingredientNew.name,
            ingredientNew.measure
        )
    }

    ////// OpenSelectedFilters

    private fun openSelectedFilters() {
        viewModelScope.launch {
            SelectedFiltersViewModel.launch(
                state.activeFilterTabIndex.intValue,
                state.selectedTags.value,
                state.selectedIngredients.value, mainViewModel
            ) { stateSelected ->
                state.selectedTags.value = stateSelected.first
                state.selectedIngredients.value = stateSelected.second
            }
        }
    }

    ///// Search

    private fun search(text: String) {
        viewModelScope.launch {
            if (state.filterEnum.value == FilterEnum.Tag || state.filterEnum.value == FilterEnum.IngredientAndTag) state.resultSearchTags.value =
                searchTags(text)
            if (state.filterEnum.value == FilterEnum.Ingredient || state.filterEnum.value == FilterEnum.IngredientAndTag) state.resultSearchIngredients.value =
                searchIngredients(text)
            if (state.filterEnum.value == FilterEnum.CategoryTag) state.resultSearchTagsCategories.value =
                searchTagsCategories(text)
            if (state.filterEnum.value == FilterEnum.CategoryIngredient) state.resultSearchIngredientsCategories.value =
                searchIngredientCategories(text)
        }
    }

    private fun searchTagsCategories(text: String): List<TagCategoryView> {
        return searchT(
            state.allTagsCategories.value,
        ) { tagCat ->
            tagCat.name.contains(text.trim(), true)
        }
    }

    private fun searchIngredientCategories(text: String): List<IngredientCategoryView> {
        return searchT(
            state.allIngredientsCategories.value,
        ) { ingredientCat ->
            ingredientCat.name.contains(text.trim(), true)
        }
    }

    private fun searchTags(text: String): List<RecipeTagView> {
        return searchT(
            state.allTagsCategories.value.flatMap { it.tags },
        ) { tag ->
            tag.tagName.contains(text.trim(), true)
        }
    }

    private fun searchIngredients(text: String): List<IngredientView> {
        return searchT(
            state.allIngredientsCategories.value.flatMap { it.ingredientViews },
        ) { ingredient ->
            ingredient.name.contains(text.trim(), true)
        }
    }

    private fun <T> searchT(
        listAll: List<T>,
        filter: (T) -> Boolean
    ): List<T> {
        return listAll.filter { filter(it) }
    }

    //// SELECT

    private fun selectTag(tag: RecipeTagView) {
        select(tag, state.selectedTags)
    }

    private fun selectIngredient(ingredient: IngredientView) {
        select(ingredient, state.selectedIngredients)
    }

    private fun selectTagCategory(tagCategory: TagCategoryView) {
        select(tagCategory, state.selectedTagsCategories)
    }

    private fun selectIngredientCategory(ingredientCat: IngredientCategoryView) {
        select(ingredientCat, state.selectedIngredientsCategories)
    }


    private fun <T> select(t: T, list: MutableState<List<T>>) {
        list.value = list.value.toMutableList().apply {
            if (state.filterMode.value == FilterMode.One) {
                add(t)
            } else {
                if (contains(t)) {
                    remove(t)
                } else {
                    add(t)
                }
            }
        }.toList()
        if (state.filterMode.value == FilterMode.One) {
            done()
        }
    }

    ////

    fun done() {
        state.searchText.value = ""
        mainViewModel.onEvent(MainEvent.NavigateBack)
        if (isForCategory) {
            resultFlowCategory!!.value = FilterResult(
                state.selectedTags.value,
                state.selectedIngredients.value,
                state.selectedTagsCategories.value,
                state.selectedIngredientsCategories.value
            )
        } else {
            resultFlow!!.value = FilterResult(
                state.selectedTags.value,
                state.selectedIngredients.value,
                state.selectedTagsCategories.value,
                state.selectedIngredientsCategories.value
            )
        }
    }

    fun close() {
        if (state.selectedTags.value.isNotEmpty()
            || state.selectedIngredientsCategories.value.isNotEmpty()
            || state.selectedIngredients.value.isNotEmpty()
            || state.selectedTagsCategories.value.isNotEmpty()
        ) {
            clear()
            clearSelected()
        } else {
            done()
        }
    }

    private fun clearSelected() {
        state.selectedTags.value = listOf()
        state.selectedTagsCategories.value = listOf()
        state.selectedIngredients.value = listOf()
        state.selectedIngredientsCategories.value = listOf()
    }

    private fun clear() {
        state.searchText.value = ""
        state.resultSearchTags.value = listOf()
        state.resultSearchIngredients.value = listOf()
        state.resultSearchTagsCategories.value = listOf()
        state.resultSearchIngredientsCategories.value = listOf()
    }

}