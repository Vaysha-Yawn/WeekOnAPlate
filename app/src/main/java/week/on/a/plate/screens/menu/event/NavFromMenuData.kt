package week.on.a.plate.screens.menu.event

sealed class NavFromMenuData {
    class NavToFullRecipe(val recId: Long, val portionsCount:Int ) : NavFromMenuData()
    data object SpecifySelection : NavFromMenuData()
}