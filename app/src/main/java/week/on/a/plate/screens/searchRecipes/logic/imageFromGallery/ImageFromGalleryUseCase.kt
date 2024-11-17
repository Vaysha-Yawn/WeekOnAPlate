package week.on.a.plate.screens.searchRecipes.logic.imageFromGallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.security.MessageDigest

class ImageFromGalleryUseCase(val vm: ViewModel) {
    lateinit var imageLauncher: ActivityResultLauncher<String>
    private val imageResultChanel = MutableStateFlow<String?>(null)

    fun saveImage(context: Context, uri: Uri?) {
        vm.viewModelScope.launch {
            uri?.let {
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    val bytes = hashImage(input.readBytes())
                    val name = "image_${bytes}.jpg"
                    val file = File(context.filesDir, name)
                    if (!file.exists()) {
                        file.outputStream().use { output ->
                            input.copyTo(output)
                            imageResultChanel.emit(name)
                        }
                    } else {
                        imageResultChanel.emit(name)
                    }
                }
            }
        }
    }

    private fun hashImage(readBytes: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(readBytes)
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun pickImage() {
        vm.viewModelScope.launch {
            imageLauncher.launch("image/*")
        }
    }

    suspend fun start(use: (String?) -> Unit) {
        imageResultChanel.emit(null)
        pickImage()
        val res = imageResultChanel.first { it != null }
        use(res)
    }
}

fun getSavedPicture(context: Context, name: String): Bitmap? {
    val file = File(context.filesDir, name)
    return if (file.exists()) {
        BitmapFactory.decodeFile(file.absolutePath)
    } else null
}