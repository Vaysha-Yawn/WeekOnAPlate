package week.on.a.plate.filters.navigation

import android.os.Bundle
import androidx.navigation.NavType
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView

object FilterCustomNavType {
    val RecipeTagView = object : NavType<List<RecipeTagView>>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): List<RecipeTagView>? {
            return CustomNavTypeFun.get(bundle, key)
        }

        override fun parseValue(value: String): List<RecipeTagView> {
            return CustomNavTypeFun.parseValue(value)
        }

        override fun serializeAsValue(value: List<RecipeTagView>): String {
            return CustomNavTypeFun.serializeAsValue(value)
        }

        override fun put(bundle: Bundle, key: String, value: List<RecipeTagView>) {
            CustomNavTypeFun.put(bundle, key, value)
        }
    }

    val IngredientView= object : NavType<List<IngredientView>>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): List<IngredientView>? {
            return CustomNavTypeFun.get(bundle, key)
        }

        override fun parseValue(value: String): List<IngredientView> {
            return CustomNavTypeFun.parseValue(value)
        }

        override fun serializeAsValue(value: List<IngredientView>): String {
            return CustomNavTypeFun.serializeAsValue(value)
        }

        override fun put(bundle: Bundle, key: String, value: List<IngredientView>) {
            CustomNavTypeFun.put(bundle, key, value)
        }
    }
}

