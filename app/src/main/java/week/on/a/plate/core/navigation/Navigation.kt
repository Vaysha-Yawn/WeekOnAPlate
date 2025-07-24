package week.on.a.plate.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.additional.createRecipe.view.base.RecipeCreateStart
import week.on.a.plate.screens.additional.deleteApply.logic.deleteApplyResultTag
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.deleteApply.view.DeleteApplyStart
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import week.on.a.plate.screens.additional.filters.logic.filterResultKey
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterResult
import week.on.a.plate.screens.additional.filters.view.FilterStart
import week.on.a.plate.screens.additional.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.additional.inventory.view.InventoryStart
import week.on.a.plate.screens.additional.ppAndTermsOfUse.navigation.DocumentsWebDestination
import week.on.a.plate.screens.additional.ppAndTermsOfUse.view.DocumentsWebStart
import week.on.a.plate.screens.additional.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.additional.recipeDetails.view.start.RecipeDetailsStart
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.navigation.SpecifyForCookPlanDestination
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.view.SpecifyForCookPlanStart
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionResultKey
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.additional.specifySelection.view.SpecifySelectionAltStart
import week.on.a.plate.screens.additional.tutorial.navigation.TutorialDestination
import week.on.a.plate.screens.additional.tutorial.view.TutorialStart
import week.on.a.plate.screens.base.cookPlanner.view.CookPlannerStart
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.view.main.MenuScreen
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.view.main.SearchStart
import week.on.a.plate.screens.base.settings.view.SettingsStart
import week.on.a.plate.screens.base.shoppingList.event.ShoppingListEvent
import week.on.a.plate.screens.base.shoppingList.view.ShoppingListStart

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = MenuDestination(),
        Modifier.padding(innerPadding)
    ) {
        //bottom bar
        composable<MenuDestination> { entry ->
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            val context = LocalContext.current
            viewModel.actionPlusButton.value =
                { viewModel.menuViewModel.onEvent(MenuEvent.GetSelIdAndCreate (context)) }
            val dateLaunch = entry.toRoute<MenuDestination>().dateLaunch

            //draft find -> find recipe -> return with recipe
            val recipeId: Long? = entry.savedStateHandle.get<Long>("recipeId")
            recipeId?.let {
                viewModel.menuViewModel.returnWithRecipeForDraft(recipeId, LocalContext.current)
                entry.savedStateHandle.remove<Long>("recipeId")
            }

            val selId: Long? = entry.savedStateHandle.get<Long>(SpecifySelectionResultKey)
            selId?.let {
                viewModel.menuViewModel.returnWithSelId(selId)
                entry.savedStateHandle.remove<Long>(SpecifySelectionResultKey)
            }

            MenuScreen(viewModel, viewModel.menuViewModel, dateLaunch)
        }

        composable<SearchDestination> { entry ->
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.searchViewModel.onEvent(SearchScreenEvent.CreateRecipe) }
            val args = entry.toRoute<SearchDestination>()

            //(Search) add recipe to menu -> Specify Selection -> (Search) return with selId
            val selId: Long? = entry.savedStateHandle.get<Long>(SpecifySelectionResultKey)
            selId?.let {
                viewModel.searchViewModel.returnWithSelIdToAdd(selId, LocalContext.current)
                entry.savedStateHandle.remove<Long>(SpecifySelectionResultKey)
            }

            SearchStart(viewModel.searchViewModel, viewModel, args)
        }
        composable<ShoppingListDestination> { entry ->
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = false
            viewModel.actionPlusButton.value =
                { viewModel.shoppingListViewModel.onEvent(ShoppingListEvent.Add) }

            val applyDeleteResult: Boolean? =
                entry.savedStateHandle.get<Boolean>(deleteApplyResultTag)
            applyDeleteResult?.let {
                viewModel.shoppingListViewModel.doAfterDeleteApply()
                entry.savedStateHandle.remove<Boolean>(deleteApplyResultTag)
            }

            ShoppingListStart(viewModel.shoppingListViewModel, viewModel)
        }
        composable<SettingsDestination> {
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActiveFilterScreen.value = false
            SettingsStart(viewModel)
        }

        composable<CookPlannerDestination> {
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveBaseScreen.value = true
            viewModel.isActiveFilterScreen.value = false
            CookPlannerStart(viewModel, viewModel.cookPlannerViewModel)
        }

        //others
        composable<SpecifySelectionDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            SpecifySelectionAltStart(viewModel.specifySelectionViewModel, viewModel)
        }

        composable<TutorialDestination> {
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            TutorialStart(viewModel)
        }

        composable<FilterDestination>() { entry ->
            val context = LocalContext.current
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = true
            viewModel.isActiveFilterScreen.value = true
            viewModel.actionPlusButton.value =
                { viewModel.filterViewModel.onEvent(FilterEvent.CreateActive(context)) }

            val args = entry.toRoute<FilterDestination>()


            val applyDeleteResult: Boolean? =
                entry.savedStateHandle.get<Boolean>(deleteApplyResultTag)
            applyDeleteResult?.let {
                viewModel.filterViewModel.afterDeleteApplied()
                entry.savedStateHandle.remove<Boolean>(deleteApplyResultTag)
            }

            FilterStart(viewModel.filterViewModel, viewModel, args)
        }

        composable<RecipeDetailsDestination> { entry ->
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false
            val args = entry.toRoute<RecipeDetailsDestination>()

            //add current recipe to menu -> select selection -> return with selId
            val selId: Long? = entry.savedStateHandle.get<Long>(SpecifySelectionResultKey)
            selId?.let {
                viewModel.recipeDetailsViewModel.returnWithSelIdToAdd(selId)
                entry.savedStateHandle.remove<FilterResult>(SpecifySelectionResultKey)
            }


            val applyDeleteResult: Boolean? =
                entry.savedStateHandle.get<Boolean>(deleteApplyResultTag)
            applyDeleteResult?.let {
                viewModel.recipeDetailsViewModel.afterDeleteApplied()
                entry.savedStateHandle.remove<Boolean>(deleteApplyResultTag)
            }

            RecipeDetailsStart(viewModel.recipeDetailsViewModel, viewModel, args)
        }

        composable<RecipeCreateDestination> { entry ->
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false

            val args = entry.toRoute<RecipeCreateDestination>()
            viewModel.recipeCreateViewModel.launch(
                args.oldRecipeId,
                args.isForCreate,
                args.recipeStart
            )


            val filterResult: FilterResult? =
                entry.savedStateHandle.get<FilterResult>(filterResultKey)
            filterResult?.let {
                viewModel.recipeCreateViewModel.applyToStateAddManyIngredients(filterResult)
                entry.savedStateHandle.remove<FilterResult>(filterResultKey)
            }

            RecipeCreateStart(viewModel.recipeCreateViewModel, viewModel)
        }

        composable<InventoryDestination> { entry ->
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false

            val args = entry.toRoute<InventoryDestination>()
            viewModel.inventoryViewModel.addMultipliesIngredientsToState(args.list)

            InventoryStart(viewModel.inventoryViewModel, viewModel)
        }

        composable<DeleteApplyDestination> { entry ->
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false

            val args = entry.toRoute<DeleteApplyDestination>()
            viewModel.deleteApplyViewModel.launch(args)

            DeleteApplyStart(viewModel.deleteApplyViewModel, viewModel)
        }

        composable<SpecifyForCookPlanDestination> { entry ->
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false

            val args = entry.toRoute<SpecifyForCookPlanDestination>()
            viewModel.specifyRecipeToCookPlanViewModel.launch(args.recipeID, args.portionsCount)

            SpecifyForCookPlanStart(viewModel.specifyRecipeToCookPlanViewModel, viewModel)
        }

        composable<DocumentsWebDestination> { entry ->
            viewModel.isActiveBaseScreen.value = false
            viewModel.isActivePlusButton.value = false
            viewModel.isActiveFilterScreen.value = false

            val args = entry.toRoute<DocumentsWebDestination>()
            viewModel.documentsWebViewModel.launch(args.isForPP)

            DocumentsWebStart(viewModel.documentsWebViewModel, viewModel)
        }
    }
}