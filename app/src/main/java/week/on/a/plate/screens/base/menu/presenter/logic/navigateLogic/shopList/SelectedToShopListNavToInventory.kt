package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.shopList

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.base.menu.domain.dbusecase.GetRecipeUseCase
import week.on.a.plate.screens.base.menu.domain.utilsUseCase.IngredientsMapByPortionsUseCase
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.state.MenuUIState
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import javax.inject.Inject

class SelectedToShopListNavToInventory @Inject constructor(
    private val getRecipe: GetRecipeUseCase,
    private val ingredientsMapByPortions: IngredientsMapByPortionsUseCase
) {
    suspend operator fun invoke(
        menuUIState: MenuUIState,
        onEvent: (MenuEvent) -> Unit,
        mainViewModel: MainViewModel,
        getSelected: (MenuUIState) -> List<Position.PositionRecipeView>
    ) = coroutineScope {
        onEvent(MenuEvent.ActionWrapperDatePicker(WrapperDatePickerEvent.SwitchEditMode))
        val selected = getSelected(menuUIState)
        val list = selected.flatMap { positionRecipeView ->
            val recipe = async { getRecipe(positionRecipeView.recipe.id) }
            val ingredients =
                ingredientsMapByPortions(positionRecipeView.portionsCount, recipe.await())
            ingredients
        }
        mainViewModel.inventoryViewModel.launchAndGetMore(list)
        mainViewModel.nav.navigate(InventoryDestination)
    }
}