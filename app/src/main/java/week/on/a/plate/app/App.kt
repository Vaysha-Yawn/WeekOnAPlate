package week.on.a.plate.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var firstStartSettingsUseCase: FirstStartSettingsUseCase

    override fun onCreate() {
        super.onCreate()
        firstStartSettingsUseCase.setStartValue(this)
    }
}