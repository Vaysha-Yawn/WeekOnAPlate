package week.on.a.plate.app.mainActivity.event

import week.on.a.plate.app.mainActivity.logic.MainViewModel

interface NavParams {
    fun launch(vm: MainViewModel)
}

object EmptyNavParams : NavParams {
    override fun launch(vm: MainViewModel) {}
}