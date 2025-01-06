package week.on.a.plate.core

import android.content.Context
import android.content.Intent
import week.on.a.plate.R

open class ShareUseCase(val context: Context) {

    protected fun shareAction(text:String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val chooserIntent = Intent.createChooser(intent, context.getString(R.string.share_via))
        context.startActivity(chooserIntent)
    }
}