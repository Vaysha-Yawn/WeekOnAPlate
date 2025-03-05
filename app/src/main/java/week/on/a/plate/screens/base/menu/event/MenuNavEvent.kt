package week.on.a.plate.screens.base.menu.event


sealed class MenuNavEvent {
    class NavToFullRecipe(val recId: Long, val portionsCount: Int) : MenuNavEvent()
    object SpecifySelection : MenuNavEvent()
}