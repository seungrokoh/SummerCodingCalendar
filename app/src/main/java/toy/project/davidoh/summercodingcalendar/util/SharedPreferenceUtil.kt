package toy.project.davidoh.summercodingcalendar.util

import android.content.Context
import toy.project.davidoh.summercodingcalendar.Global.PREF_DEFAULT_VALUE
import toy.project.davidoh.summercodingcalendar.Global.PREF_NAME

class SharedPreferenceUtil(val context: Context) {

    fun put(key: String, value: String) {
        val pref = context.applicationContext.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()

        editor.putString(key, value)
        editor.commit()
    }

    fun get(key: String): String {
        val pref = context.applicationContext.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return pref.getString(key, PREF_DEFAULT_VALUE)
    }
}