package week.on.a.plate.fullScreenDialogs.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.filters.navigation.CustomNavTypeFun

object CustomNavType {
    val PositionRecipeView = object : NavType<Position.PositionRecipeView>(isNullableAllowed = false){
        override fun get(bundle: Bundle, key: String): Position.PositionRecipeView? {
            return CustomNavTypeFun.get(bundle, key)
        }

        override fun parseValue(value: String): Position.PositionRecipeView {
            return CustomNavTypeFun.parseValue(value)
        }

        override fun serializeAsValue(value: Position.PositionRecipeView): String {
            return CustomNavTypeFun.serializeAsValue(value)
        }

        override fun put(bundle: Bundle, key: String, value: Position.PositionRecipeView) {
            CustomNavTypeFun.put(bundle, key, value)
        }
    }

    val Position = object : NavType<Position>(isNullableAllowed = false){
        override fun get(bundle: Bundle, key: String): Position? {
            return CustomNavTypeFun.get(bundle, key)
        }

        override fun parseValue(value: String): Position {
            return CustomNavTypeFun.parseValue(value)
        }

        override fun serializeAsValue(value: Position): String {
            return CustomNavTypeFun.serializeAsValue(value)
        }

        override fun put(bundle: Bundle, key: String, value: Position) {
            CustomNavTypeFun.put(bundle, key, value)
        }
    }
}