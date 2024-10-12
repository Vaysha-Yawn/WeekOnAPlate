package week.on.a.plate.screens.menu.event

sealed class NavFromMenuData {
    class NavToFullRecipe(val recId: Long, val portionsCount:Int ) : week.on.a.plate.screens.menu.event.NavFromMenuData()
    data object SpecifySelection : week.on.a.plate.screens.menu.event.NavFromMenuData()
}