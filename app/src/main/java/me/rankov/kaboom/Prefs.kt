package me.rankov.kaboom

import android.content.Context

class Prefs(context: Context) {
    val PREFS_FILENAME = "me.rankov.kaboom.prefs"
    val COUNTRY = "country"
    val NICKNAME = "nickname"

    val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var country: Int
        get() = prefs.getInt(COUNTRY, -1)
        set(value) = prefs.edit().putInt(COUNTRY, value).apply()

    var nickname: String?
        get() = prefs.getString(NICKNAME, "")
        set(value) = prefs.edit().putString(NICKNAME, value).apply()
}