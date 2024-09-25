package week.on.a.plate.screenRecipeDetails.util

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock

fun setTimer(act: Context, timeMin:Long){
    val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
        putExtra(AlarmClock.EXTRA_MESSAGE, "")
        putExtra(AlarmClock.EXTRA_LENGTH, timeMin.toInt())
        putExtra(AlarmClock.EXTRA_SKIP_UI, false)
    }
    act.startActivity(intent)
}