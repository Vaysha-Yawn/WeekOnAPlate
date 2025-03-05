package week.on.a.plate.screens.additional.filters.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogs.editOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import week.on.a.plate.screens.additional.filters.logic.ingredient.IngredientCRUD
import week.on.a.plate.screens.additional.filters.logic.ingredientCategory.IngredientCategoryCRUD
import week.on.a.plate.screens.additional.filters.logic.tag.TagCRUD
import week.on.a.plate.screens.additional.filters.logic.tagCategory.TagCategoryCRUD
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterResult
import week.on.a.plate.screens.additional.filters.state.FilterUIState
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val ingredientCategoryRepository: IngredientCategoryRepository,
    private val recipeTagCategoryRepository: RecipeTagCategoryRepository,
    private val voiceSearch: VoiceSearchUseCase,
    private val ingredientCRUD: IngredientCRUD,
    private val ingredientCategoryCRUD: IngredientCategoryCRUD,
    private val tagCRUD: TagCRUD,
    private val tagCategoryCRUD: TagCategoryCRUD,
    private val searchUseCase: SearchUseCase,
    private val selectUseCase: SelectUseCase
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
            is FilterEvent.SearchFilter -> viewModelScope.launch {
                searchUseCase.search(event.text, allTags.value, allIngredients.value, state)
            }

            FilterEvent.SelectedFilters -> openSelectedFilters()
            is FilterEvent.VoiceSearchFilters -> voiceSearch(
                event.context,
                mainViewModel,
                ::onEvent,
                state.searchText,
                viewModelScope,
                state,
            )

            FilterEvent.ClearSearch -> clear()

            is FilterEvent.CreateIngredient -> ingredientCRUD.createIngredient(
                event.context,
                viewModelScope,
                mainViewModel,
                state.searchText.value,
                allIngredients.value,
                ::onEvent
            )

            is FilterEvent.CreateTag -> tagCRUD.createTag(
                ::onEvent,
                viewModelScope,
                state.searchText.value,
                mainViewModel,
                allTags.value
            )

            is FilterEvent.CreateIngredientCategory -> ingredientCategoryCRUD.createIngredientCategory(
                ::onEvent,
                viewModelScope,
                state.searchText.value,
                mainViewModel
            )

            is FilterEvent.CreateTagCategory -> tagCategoryCRUD.createTagCategory(
                ::onEvent,
                viewModelScope,
                state.searchText.value,
                mainViewModel
            )

            is FilterEvent.SelectIngredient -> selectUseCase.selectIngredient(
                event.ingredient,
                state.selectedIngredients,
                state.filterMode.value,
                ::onEvent
            )

            is FilterEvent.SelectTag -> selectUseCase.selectTag(
                event.tag,
                state.selectedTags,
                state.filterMode.value,
                ::onEvent
            )

            is FilterEvent.SelectIngredientCategory -> selectUseCase.selectIngredientCategory(
                event.ingredientCategoryView,
                state.selectedIngredientsCategories,
                state.filterMode.value,
                ::onEvent
            )

            is FilterEvent.SelectTagCategory -> selectUseCase.selectTagCategory(
                event.tagCategoryView,
                state.selectedTagsCategories,
                state.filterMode.value,
                ::onEvent
            )

            is FilterEvent.EditOrDeleteIngredient -> editOrDelete(
                delete = {
                    viewModelScope.launch {
                        ingredientCRUD.deleteIngredient(
                            event.ingredient,
                            event.context,
                            mainViewModel
                        )
                    }
                },
                edit = {
                    viewModelScope.launch {
                        ingredientCRUD.editIngredient(
                            event.context,
                            event.ingredient,
                            mainViewModel,
                            viewModelScope,
                            allIngredients.value
                        )
                    }
                })

            is FilterEvent.EditOrDeleteTag -> editOrDelete(
                delete = {
                    viewModelScope.launch {
                         tagCRUD.deleteTag(
                            event.tag,
                            event.context,
                            mainViewModel
                        )
                    }
                },
                edit = { viewModelScope.launch { tagCRUD.editTag(event.tag, mainViewModel,  viewModelScope,
                    allTags.value) } })

            is FilterEvent.EditOrDeleteIngredientCategory -> {
                if (event.ingredientCategory.id == 1L) return
                editOrDelete(
                    delete = {
                        viewModelScope.launch {
                            ingredientCategoryCRUD.deleteIngredientCategory(
                                event.ingredientCategory
                            )
                        }
                    },
                    edit = {
                        viewModelScope.launch {
                            ingredientCategoryCRUD.editIngredientCategory(
                                event.ingredientCategory,
                                mainViewModel, viewModelScope
                            )
                        }
                    })
            }

            is FilterEvent.EditOrDeleteTagCategory -> {
                if (event.tagCategory.id == 1L) return
                editOrDelete(
                    delete = { viewModelScope.launch { tagCategoryCRUD.deleteTagCategory(event.tagCategory) } },
                    edit = {
                        viewModelScope.launch {
                            tagCategoryCRUD.editTagCategory(
                                event.tagCategory,
                                mainViewModel,
                                viewModelScope
                            )
                        }
                    })
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