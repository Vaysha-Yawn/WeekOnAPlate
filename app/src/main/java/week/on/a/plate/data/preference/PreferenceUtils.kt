package week.on.a.plate.data.preference

import android.content.Context

object PreferenceUtils {
    private const val SETTING_FILE = "Settings"

    private fun getPref (context:Context) = context.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE)!!

    fun getInt(valueName:String, context:Context): Int {
        val pref = getPref(context)
        return pref.getInt(valueName, 0)
    }

    fun saveInt(valueName:String, i:Int,context:Context){
        val pref = getPref(context)
        pref.edit().putInt(valueName, i).apply()
    }

    fun getBool(valueName:String, context:Context): Boolean {
        val pref = getPref(context)
        return pref.getBoolean(valueName, false)
    }

    fun saveBool(valueName:String, i:Boolean,context:Context){
        val pref = getPref(context)
        pref.edit().putBoolean(valueName, i).apply()
    }

    fun getString(valueName:String, context:Context): String {
        val pref = getPref(context)
        return pref.getString(valueName, "")?:""
    }

    fun saveString(valueName:String, i:String,context:Context){
        val pref = getPref(context)
        pref.edit().putString(valueName, i).apply()
    }
}