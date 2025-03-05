package week.on.a.plate.data.preference

import android.content.Context
import week.on.a.plate.app.mainActivity.view.MainActivity

class PreferenceUseCase {
    val utils = PreferenceUtils

    fun getActiveThemeId(context: Context): Int {
        return PreferenceUtils.getInt(ACTIVE_THEME_ID, context)
    }

    fun saveActiveThemeId(context: Context, themeId: Int) {
        PreferenceUtils.saveInt(ACTIVE_THEME_ID, themeId, context)
    }

    fun getIsLargeFont(context: Context): Boolean {
        return PreferenceUtils.getBool(IS_LARGE_FONT_ACTIVATE, context)
    }

    fun saveIsLargeFont(context: Context, value: Boolean) {
        PreferenceUtils.saveBool(IS_LARGE_FONT_ACTIVATE, value, context)
    }

    fun getIsDefaultSearchIsCard(context: Context): Boolean {
        return PreferenceUtils.getBool(IS_DEFAULT_SEARCH_IS_CARD, context)
    }

    fun saveIsDefaultSearchIsCard(context: Context, value: Boolean) {
        PreferenceUtils.saveBool(IS_DEFAULT_SEARCH_IS_CARD, value, context)
    }

    fun getDefaultPortionsCount(context: Context): Int {
        return PreferenceUtils.getInt(DEFAULT_PORTIONS_COUNT, context)
    }

    fun saveDefaultPortionsCount(context: Context, count: Int) {
        PreferenceUtils.saveInt(DEFAULT_PORTIONS_COUNT, count, context)
    }

    fun restartActivity(context: Context) {
        /*val i = Intent(context, MainActivity::class.java)
        i.flags.plus(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        context.startActivity(i)*/
        (context as MainActivity).recreate()
    }

    companion object {
        // open settings with ui
        const val DEFAULT_PORTIONS_COUNT = "DEFAULT_PORTIONS_COUNT"
        const val IS_DEFAULT_SEARCH_IS_CARD = "IS_DEFAULT_SEARCH_IS_CARD"
        const val ACTIVE_THEME_ID = "ACTIVE_THEME_ID"
        const val IS_LARGE_FONT_ACTIVATE = "IS_LARGE_FONT_ACTIVATE"

        // hidden properties
        const val IS_APP_RATED = "IS_APP_RATED"
        const val IS_PREMIUM_ACTIVE =
            "IS_PREMIUM_ACTIVE"// скорее чтобы не переспрашивать, но в основном это значение должно передоваться из сервера
        const val IS_START_BLANK_COMPLETED = "IS_START_BLANK_COMPLETED"
        const val IS_ONBOARDING_COMPLETED = "IS_ONBOARDING_COMPLETED"
        const val IS_TUTORIAL_MENU_COMPLETED = "IS_TUTORIAL_MENU_COMPLETED"
        const val IS_TUTORIAL_RECIPE_CREATE_COMPLETED = "IS_TUTORIAL_RECIPE_CREATE_COMPLETED"
        const val IS_TUTORIAL_SHOPPING_LIST_COMPLETED = "IS_TUTORIAL_MENU_COMPLETED"
        const val IS_TUTORIAL_RECIPE_DETAILS_COMPLETED = "IS_TUTORIAL_MENU_COMPLETED"
        const val IS_TUTORIAL_FILTERS_COMPLETED = "IS_TUTORIAL_MENU_COMPLETED"
        const val IS_TUTORIAL_COOK_PLANNER_COMPLETED = "IS_TUTORIAL_MENU_COMPLETED"
        const val IS_TUTORIAL_SEARCH_COMPLETED = "IS_TUTORIAL_MENU_COMPLETED"
    }
}