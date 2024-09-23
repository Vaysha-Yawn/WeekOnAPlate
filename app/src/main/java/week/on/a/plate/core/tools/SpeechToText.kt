package week.on.a.plate.core.tools

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import week.on.a.plate.core.MainActivity


private fun startVoiceRecognitionActivity(activity: MainActivity) {
    //todo replace with activity result api
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
   val i = activity.startActivityForResult(intent, 0)
}