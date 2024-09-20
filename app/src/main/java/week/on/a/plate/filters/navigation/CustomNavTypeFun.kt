package week.on.a.plate.filters.navigation

import android.net.Uri
import android.os.Bundle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavTypeFun{
    inline fun <reified T> get(bundle: Bundle, key: String): T? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }

    inline fun <reified T> parseValue(value: String): T {
        return Json.decodeFromString(Uri.decode(value))
    }

    inline fun <reified T> serializeAsValue(value: T): String {
        return Uri.encode(Json.encodeToString(value))
    }

    inline fun <reified T> put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(value))
    }
}