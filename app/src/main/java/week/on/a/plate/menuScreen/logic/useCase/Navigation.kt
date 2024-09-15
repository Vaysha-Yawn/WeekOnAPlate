package week.on.a.plate.menuScreen.logic.useCase

import androidx.navigation.NavHostController
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView
import javax.inject.Inject

class Navigation @Inject constructor() {

    lateinit var navController: NavHostController

    fun actionNavToFullRecipe(rec: RecipeShortView){
       // navController.navigate()
        //navController.popBackStack()
    }

    fun actionShowEditDialog(id: Position){

    }

    //1. сохранить день и категорию и открыть поиск
    fun actionToFindRecipe() {

    }
        //2. Выбрать рецепт и перйти обратно
    fun actionShowAddRecipeToMenuDialog(){

    }

     // к инвентаризации
    fun actionSelectedToShopList(value: List<Long>) {

    }

}