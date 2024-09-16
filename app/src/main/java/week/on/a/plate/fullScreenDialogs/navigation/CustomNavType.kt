package week.on.a.plate.fullScreenDialogs.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import week.on.a.plate.core.data.week.Position

object CustomNavType {
    val PositionRecipeView = object : NavType<Position.PositionRecipeView>(isNullableAllowed = false){
        override fun get(bundle: Bundle, key: String): Position.PositionRecipeView? {
            return Json.decodeFromString(bundle.getString(key)?:return null)
        }

        override fun parseValue(value: String): Position.PositionRecipeView {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Position.PositionRecipeView): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Position.PositionRecipeView) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

    val Position = object : NavType<Position>(isNullableAllowed = false){
        override fun get(bundle: Bundle, key: String): Position? {
            return Json.decodeFromString(bundle.getString(key)?:return null)
        }

        override fun parseValue(value: String): Position {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Position): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Position) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}