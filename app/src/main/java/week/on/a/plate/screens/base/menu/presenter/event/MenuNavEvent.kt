package week.on.a.plate.screens.base.menu.presenter.event


sealed class MenuNavEvent {
    class NavToFullRecipe(val recId: Long, val portionsCount: Int) : MenuNavEvent()
}