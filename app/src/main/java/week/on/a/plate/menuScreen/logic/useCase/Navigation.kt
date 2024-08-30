package week.on.a.plate.menuScreen.logic.useCase

import week.on.a.plate.core.data.recipe.RecipeView
import javax.inject.Inject

class Navigation @Inject constructor() {

    fun actionNavToFullRecipe(rec: RecipeView){

    }

    fun actionShowEditDialog(id:Long){

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