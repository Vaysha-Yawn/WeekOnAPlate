package week.on.a.plate.app.mainActivity.logic.takePicture

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import java.io.File
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TakePictureUseCase(val vm: MainViewModel) {
    lateinit var imageLauncher: ActivityResultLauncher<Uri>
    private val imageResultChanel = MutableStateFlow<String?>(null)
    private var templateUri: Uri? = null
    private var name: String? = null

    fun saveImage(context: Context) {
        vm.viewModelScope.launch(Dispatchers.IO) {
            templateUri?.let {
                val inputStream = context.contentResolver.openInputStream(templateUri!!)
                inputStream?.use { input ->
                    val bytes = input.readBytes()
                    val file = File(context.filesDir, name!!)
                    if (!file.exists()) {
                        file.outputStream().use { output ->
                            output.write(bytes)
                            imageResultChanel.emit(name)
                        }
                    } else {
                        imageResultChanel.emit(name)
                    }
                }
            }
        }
    }

    suspend fun start(
        context: Context,
        use: (String?) -> Unit
    ) {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            name = "image_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm:ss"))}.jpg"
            val file = File(context.filesDir, name!!)
            templateUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file)
            imageLauncher.launch(templateUri!!)
            val res = imageResultChanel.first { it != null }
            use(res)
        } else {
            vm.onEvent(MainEvent.ShowSnackBar(context.getString(R.string.no_camera_hint)))
        }
    }

    fun close() {
        vm.viewModelScope.launch {
            imageResultChanel.emit(null)
            templateUri = null
            name = null
        }
    }
}