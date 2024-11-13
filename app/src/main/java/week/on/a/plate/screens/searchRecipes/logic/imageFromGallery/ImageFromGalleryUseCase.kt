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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ImageFromGalleryUseCase(val vm: ViewModel) {
    lateinit var imageLauncher: ActivityResultLauncher<String>
    private val imageResultChanel = MutableStateFlow<String?>(null)

    fun saveImage(context: Context, uri: Uri?) {
        vm.viewModelScope.launch {
            uri?.let {
                val inputStream = context.contentResolver.openInputStream(uri)
                val name = "image_for_recipe" + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy_MM_dd__HH:mm")
                ) + ".jpg"
                val file = File(context.filesDir, name)
                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                        imageResultChanel.emit(name)
                    }
                }
            }
        }
    }

    private fun pickImage() {
        vm.viewModelScope.launch {
            imageLauncher.launch("image/*")
        }
    }

    suspend fun start( use:(String?)->Unit){
        imageResultChanel.emit(null)
        pickImage()
        val res = imageResultChanel.first { it!=null }
        use(res)
    }
}

fun getSavedPicture(context: Context, name: String): Bitmap? {
    val file = File(context.filesDir, name)
    return if (file.exists()) {
        BitmapFactory.decodeFile(file.absolutePath)
    } else null
}