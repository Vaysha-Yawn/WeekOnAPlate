package week.on.a.plate.search.logic.voice

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class VoiceInputUseCase() {
    lateinit var voiceInputLauncher: ActivityResultLauncher<Intent>
    private val voiceResultChanel = MutableStateFlow<ArrayList<String>?>(null)

    private suspend fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }
        voiceInputLauncher.launch(intent)
    }

    suspend fun processVoiceResult(recognizedText: ArrayList<String>?) {
        voiceResultChanel.emit(recognizedText)
    }

    suspend fun start( use:(ArrayList<String>?)->Unit){
        voiceResultChanel.emit(null)
        startListening()
        val res = voiceResultChanel.first { it!=null }
        use(res)
    }

}