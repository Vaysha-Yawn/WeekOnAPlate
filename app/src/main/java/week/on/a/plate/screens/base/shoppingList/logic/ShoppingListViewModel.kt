package week.on.a.plate.screens.base.shoppingList.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.base.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.base.shoppingList.state.ShoppingListUIState
import javax.inject.Inject


@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val addIngredient: AddIngredientUseCase,
    private val deleteApply: DeleteApplyUseCase,
    private val deleteChecked: DeleteCheckedUseCase,
    private val updateCheck: UpdateCheckUseCase,
    private val editIngredient: EditIngredientUseCase
) : ViewModel() {

    val state = ShoppingListUIState()

    lateinit var allItemsChecked: StateFlow<List<IngredientInRecipeView>>
    lateinit var allItemsUnChecked: StateFlow<List<IngredientInRecipeView>>

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    init {
        viewModelScope.launch {
            allItemsChecked = shoppingItemRepository.getCheckedFlow().map { viewList ->
                viewList.map { shoppingItemView -> shoppingItemView.ingredientInRecipe }
            }.stateIn(viewModelScope)
            allItemsUnChecked = shoppingItemRepository.getUnCheckedFlow().map { viewList ->
                viewList.map { shoppingItemView -> shoppingItemView.ingredientInRecipe }
            }.stateIn(viewModelScope)
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainEvent.value = event
            is ShoppingListEvent -> onEvent(event)
        }
    }

    fun onEvent(event: ShoppingListEvent) {
        viewModelScope.launch {
            when (event) {
                ShoppingListEvent.Add -> addIngredient(
                    { event -> mainEvent.value = event },
                    allItemsUnChecked.value
                )

                is ShoppingListEvent.Check -> updateCheck(true, event.position)
                ShoppingListEvent.DeleteChecked -> deleteChecked()
                is ShoppingListEvent.Uncheck -> updateCheck(false, event.position)
                is ShoppingListEvent.Edit -> editIngredient(
                    event.ingredient,
                    dialogOpenParams
                )

                is ShoppingListEvent.Share -> ShareShoppingListUseCase(event.context).shareShoppingList(
                    state.listUnchecked.value
                )

                is ShoppingListEvent.DeleteAll -> deleteApply(event.context) { event ->
                    mainEvent.value = event
                }
            }
        }
    }
}