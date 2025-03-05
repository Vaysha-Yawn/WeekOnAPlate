package week.on.a.plate.core.uitools.webview

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.webkit.WebView
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event

fun copyLinkToClipboard(context: Context, url: String?, onEvent:(Event)->Unit) {
    if (url != null) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Image URL", url)
        clipboard.setPrimaryClip(clip)
        onEvent(MainEvent.ShowSnackBar(context.getString(R.string.mess_image_link_copied)))
    }
}

fun WebView.detectTouch(onEvent:(Event)->Unit):Boolean{
    val result = this.getHitTestResult()
    return if (result.type == WebView.HitTestResult.IMAGE_TYPE ||
        result.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE
    ) {
        val imageUrl = result.extra
        copyLinkToClipboard(context, imageUrl, onEvent)
        true
    } else {
        false
    }
}