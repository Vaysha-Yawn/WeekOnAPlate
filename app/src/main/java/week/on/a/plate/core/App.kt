package week.on.a.plate.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application(){
    override fun onCreate() {
        super.onCreate()
        //sCRUDRecipeInMenu.menuR.insertNewWeek(WeekDataExample)
    }
}