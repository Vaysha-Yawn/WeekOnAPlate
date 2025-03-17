package week.on.a.plate.screens.additional.recipeDetails.logic.utils

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock

fun setTimer(act: Context, timeSec:Int){
    val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
        putExtra(AlarmClock.EXTRA_MESSAGE, "")
        putExtra(AlarmClock.EXTRA_LENGTH, timeSec)
        putExtra(AlarmClock.EXTRA_SKIP_UI, false)
    }
    act.startActivity(intent)
}