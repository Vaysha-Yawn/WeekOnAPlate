package week.on.a.plate.recipeFullScreen.util

import android.app.Activity
import android.content.Intent
import android.provider.AlarmClock

fun setTimer(act:Activity, timeMin:Int){
    val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
        putExtra(AlarmClock.EXTRA_MESSAGE, "")
        putExtra(AlarmClock.EXTRA_LENGTH, timeMin)
        putExtra(AlarmClock.EXTRA_SKIP_UI, false)
    }
    act.startActivity(intent)
}