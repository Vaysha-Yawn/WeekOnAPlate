package week.on.a.plate.app.mainActivity.logic.voice

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel

class VoiceInputUseCase(val vm: MainViewModel) {
    lateinit var voiceInputLauncher: ActivityResultLauncher<Intent>
    private val voiceResultChanel = MutableStateFlow<ArrayList<String>?>(null)

    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }
        try {
            voiceInputLauncher.launch(intent)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    suspend fun processVoiceResult(recognizedText: ArrayList<String>?) {
        voiceResultChanel.emit(recognizedText)
    }

    suspend fun start(context: Context, use: (ArrayList<String>?) -> Unit){
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            voiceResultChanel.emit(null)
            startListening()
            val res = voiceResultChanel.first { it!=null }
            use(res)
        } else {
            vm.onEvent(MainEvent.ShowSnackBar(context.getString(R.string.no_mic_hint)))
        }


    }

}