package week.on.a.plate.screenFilters.logic

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.startCategoryName
import week.on.a.plate.dialogAddIngredient.logic.AddIngredientViewModel
import week.on.a.plate.dialogAddTag.logic.AddTagViewModel
import week.on.a.plate.dialogEditOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogEditOneString.state.EditOneStringUIState
import week.on.a.plate.dialogEditOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogEditOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenDeleteApply.event.DeleteApplyEvent
import week.on.a.plate.screenDeleteApply.navigation.DeleteApplyDirection
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screenFilters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.screenFilters.event.FilterEvent
import week.on.a.plate.screenFilters.state.FilterEnum
import week.on.a.plate.screenFilters.state.FilterMode
import week.on.a.plate.screenFilters.state.FilterResult
import week.on.a.plate.screenFilters.state.FilterUIState
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val ingredientCategoryRepository: IngredientCategoryRepository,
    private val recipeTagCategoryRepository: RecipeTagCategoryRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeTagRepository: RecipeTagRepository,
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
        lastFilters: Pair<List<RecipeTagView>, List<IngredientView>>?, isForCategory:Boolean,
        use: suspend (FilterResult) -> Unit
    ) {
        state.selectedTags.value = lastFilters?.first ?: listOf()
        state.selectedIngredients.value = lastFilters?.second ?: listOf()
        state.filterMode.value = mode
        state.filterEnum.value = enum

        this.isForCategory = isForCategory
        if (isForCategory){
            resultFlowCategory = MutableStateFlow<FilterResult?>(null)
            resultFlowCategory!!.collect { value ->
                if (value != null) {
                    use(value)
                }
            }
        }else{
            resultFlow = MutableStateFlow<FilterResult?>(null)
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
            FilterEvent.VoiceSearchFilters -> voiceSearch()
            FilterEvent.ClearSearch -> clear()

            is FilterEvent.CreateIngredient -> toCreateIngredient()
            is FilterEvent.CreateTag -> toCreateTag()
            is FilterEvent.CreateIngredientCategory -> toCreateIngredientCategory()
            is FilterEvent.CreateTagCategory -> toCreateTagCategory()

            is FilterEvent.SelectIngredient -> selectIngredient(event.ingredient)
            is FilterEvent.SelectTag -> selectTag(event.tag)
            is FilterEvent.SelectIngredientCategory -> selectIngredientCategory(event.ingredientCategoryView)
            is FilterEvent.SelectTagCategory -> selectTagCategory(event.tagCategoryView)

            is FilterEvent.EditOrDeleteIngredient -> editOrDelete(
                delete = { deleteIngredient(event.ingredient) },
                edit = { editIngredient(event.ingredient) })

            is FilterEvent.EditOrDeleteTag -> editOrDelete(
                delete = { deleteTag(event.tag) },
                edit = { editTag(event.tag) })

            is FilterEvent.EditOrDeleteIngredientCategory -> {
                if (event.ingredientCategory.name == startCategoryName) return
                editOrDelete(
                    delete = { deleteIngredientCategory(event.ingredientCategory) },
                    edit = { editIngredientCategory(event.ingredientCategory) })
            }

            is FilterEvent.EditOrDeleteTagCategory -> {
                if (event.tagCategory.name == startCategoryName) return
                editOrDelete(
                    delete = { deleteTagCategory(event.tagCategory) },
                    edit = { editTagCategory(event.tagCategory) })
            }

            FilterEvent.CreateActive -> {
                createFilterElement()
            }
        }
    }

    private fun createFilterElement() {
        when (state.filterEnum.value) {
            FilterEnum.Ingredient -> onEvent(FilterEvent.CreateIngredient)
            FilterEnum.Tag -> onEvent(FilterEvent.CreateTag)
            FilterEnum.CategoryTag -> onEvent(FilterEvent.CreateTagCategory)
            FilterEnum.CategoryIngredient -> onEvent(FilterEvent.CreateIngredientCategory)
            FilterEnum.IngredientAndTag -> if (state.activeFilterTabIndex.intValue == 0) {
                onEvent(FilterEvent.CreateTag)
            } else {
                onEvent(FilterEvent.CreateIngredient)
            }
        }
    }

    private fun editOrDelete(delete: () -> Unit, edit: () -> Unit) {
        val vm = EditOrDeleteViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        viewModelScope.launch {
            vm.launchAndGet { event ->
                when (event) {
                    EditOrDeleteEvent.Close -> {}
                    EditOrDeleteEvent.Delete -> delete()
                    EditOrDeleteEvent.Edit -> edit()
                }
            }
        }
    }

    // DELETE

    private fun deleteIngredient(ingredient: IngredientView) {
        viewModelScope.launch {
            val vmDel = mainViewModel.deleteApplyViewModel
            val mes = "Вы уверены, что хотите удалить этот ингредиент?\n" +
                    "Внимание, при удалении ингредиента он удалится из рецептов, меню (позиции этого ингредиента, в набросках) и списка покупок.\n" +
                    "Это действие нельзя отменить."
            mainViewModel.nav.navigate(DeleteApplyDirection)
            vmDel.launchAndGet(mes) { event ->
                if (event == DeleteApplyEvent.Apply) {
                    ingredientRepository.delete(ingredient.ingredientId)
                }
            }
        }
    }

    private fun deleteTag(tag: RecipeTagView) {
        viewModelScope.launch {
            val vmDel = mainViewModel.deleteApplyViewModel
            val mes = "Вы уверены, что хотите удалить этот тэг?\n" +
                    "Внимание, при удалении тэга он удалится из всех набросков и рецептов.\n" +
                    "Это действие нельзя отменить."
            mainViewModel.nav.navigate(DeleteApplyDirection)
            vmDel.launchAndGet(mes) { event ->
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
        val vm = EditOneStringViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        viewModelScope.launch {
            vm.launchAndGet(
                EditOneStringUIState(
                    oldIngredientCat.name,
                    "Редактировать название категории",
                    "Введите название категории здесь..."
                )
            ) { newName ->
                ingredientCategoryRepository.updateName(newName, oldIngredientCat.id)
            }
        }
    }

    private fun editTagCategory(oldTagCat: TagCategoryView) {
        val vm = EditOneStringViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        viewModelScope.launch {
            vm.launchAndGet(
                EditOneStringUIState(
                    oldTagCat.name,
                    "Редактировать название категории",
                    "Введите название категории здесь..."
                )
            ) { newName ->
                recipeTagCategoryRepository.updateName(newName, oldTagCat.id)
            }
        }
    }

    private fun editTag(tag: RecipeTagView) {
        val vm = AddTagViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        val oldCategory = allTags.value.find { it.tags.contains(tag) }
        viewModelScope.launch {
            vm.launchAndGet(tag.tagName, oldCategory, oldCategory!!) { newNameAndCategory ->
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

    private fun editIngredient(ingredient: IngredientView) {
        val vm = AddIngredientViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        val oldCategory = allIngredients.value.find { it.ingredientViews.contains(ingredient) }!!
        viewModelScope.launch {
            vm.launchAndGet(ingredient, oldCategory, oldCategory) { newIngredientAndCategory ->
                editIngredientDB(
                    ingredient,
                    newIngredientAndCategory.first,
                    newIngredientAndCategory.second
                )
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

    /// VoiceSearch

    private fun voiceSearch() {
        mainViewModel.onEvent(MainEvent.VoiceToText() { strings: ArrayList<String>? ->
            if (strings == null) return@VoiceToText
            viewModelScope.launch {
                val searchedList = strings.getOrNull(0)?.split(" ") ?: return@launch

                val listIngredientView = mutableListOf<IngredientView>()
                val listTags = mutableListOf<RecipeTagView>()

                searchedList.forEach {
                    val res = getAllSearch(it)
                    if (res.tags != null) listTags.addAll(res.tags)
                    if (res.ingredients != null) listIngredientView.addAll(res.ingredients)
                }

                if (listIngredientView.isEmpty() && listTags.isEmpty()) {
                    state.searchText.value = strings.joinToString()
                    return@launch
                }

                val vm = FilterVoiceApplyViewModel()
                vm.mainViewModel = mainViewModel
                mainViewModel.onEvent(MainEvent.OpenDialog(vm))
                vm.launchAndGet(listTags, listIngredientView) { stateApply ->
                    stateApply.selectedTags.value.forEach {
                        onEvent(FilterEvent.SelectTag(it))
                    }
                    stateApply.selectedIngredients.value.forEach {
                        onEvent(FilterEvent.SelectIngredient(it))
                    }
                }
            }
        })
    }

    private fun getAllSearch(name: String): FilterResult {

        val tags =
            if (state.filterEnum.value == FilterEnum.Tag || state.filterEnum.value == FilterEnum.IngredientAndTag) searchTags(
                name
            ) else null
        val ingredients =
            if (state.filterEnum.value == FilterEnum.Ingredient || state.filterEnum.value == FilterEnum.IngredientAndTag) searchIngredients(
                name
            ) else null
        val tagsCategories =
            if (state.filterEnum.value == FilterEnum.CategoryTag) searchTagsCategories(name) else null
        val ingredientsCategories =
            if (state.filterEnum.value == FilterEnum.CategoryIngredient) searchIngredientCategories(
                name
            ) else null

        return FilterResult(tags, ingredients, tagsCategories, ingredientsCategories)
    }

    /// CREATE

    private fun toCreateTag() {
        viewModelScope.launch {
            val vm = AddTagViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            val defCategoryView = allTags.value.find { it.name == "Без категории" }!!
            vm.launchAndGet(state.searchText.value, null, defCategoryView) { tagData ->
                insertNewTagInDB(tagData)
            }
        }
    }

    private suspend fun insertNewTagInDB(tagData: Pair<String, TagCategoryView>) {
        val newTagId = recipeTagRepository.insert(tagData.first, tagData.second.id)
        val newTag = recipeTagRepository.getById(newTagId)
        if (newTag != null) onEvent(FilterEvent.SelectTag(newTag))
    }

    private fun toCreateIngredient() {
        viewModelScope.launch {
            val vm = AddIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            val oldIngredient = IngredientView(
                0,
                img = "",
                name = state.searchText.value,
                measure = ""
            )
            val defCategoryView = allIngredients.value.find { it.name == "Без категории" }!!
            vm.launchAndGet(oldIngredient, null, defCategoryView) { ingredientData ->
                insertNewIngredientInDB(ingredientData)
            }
        }
    }

    private suspend fun insertNewIngredientInDB(ingredientData: Pair<IngredientView, IngredientCategoryView>) {
        val newIngredientId = ingredientRepository.insert(
            categoryId = ingredientData.second.id,
            img = ingredientData.first.img,
            name = ingredientData.first.name,
            measure = ingredientData.first.measure
        )
        val newIngredient = ingredientRepository.getById(newIngredientId)
        if (newIngredient != null) onEvent(FilterEvent.SelectIngredient(newIngredient))
    }

    private fun toCreateTagCategory() {
        viewModelScope.launch {
            val vm = EditOneStringViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                EditOneStringUIState(
                    state.searchText.value,
                    "Добавить категорию",
                    "Введите название категории здесь..."
                )
            ) { name ->
                insertNewTagCategoryInDB(name)
            }
        }
    }

    private suspend fun insertNewTagCategoryInDB(name: String) {
        val id = recipeTagCategoryRepository.create(name)
        val tagCategoryView = recipeTagCategoryRepository.getById(id)
        if (tagCategoryView != null) onEvent(FilterEvent.SelectTagCategory(tagCategoryView))
    }

    private fun toCreateIngredientCategory() {
        viewModelScope.launch {
            val vm = EditOneStringViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                EditOneStringUIState(
                    state.searchText.value,
                    "Добавить категорию",
                    "Введите название категории здесь..."
                )
            ) { name ->
                insertNewIngredientCategoryInDB(name)
            }
        }
    }

    private suspend fun insertNewIngredientCategoryInDB(name: String) {
        val id = ingredientCategoryRepository.create(name)
        val ingredientCategory = ingredientCategoryRepository.getById(id)
        if (ingredientCategory != null) onEvent(
            FilterEvent.SelectIngredientCategory(
                ingredientCategory
            )
        )
    }

    ////// OpenSelectedFilters

    private fun openSelectedFilters() {
        viewModelScope.launch {
            val vm = SelectedFiltersViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                state.activeFilterTabIndex.intValue,
                state.selectedTags.value,
                state.selectedIngredients.value
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
        }else{
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