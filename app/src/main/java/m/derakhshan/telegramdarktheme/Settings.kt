package m.derakhshan.telegramdarktheme

import android.content.Context
import android.util.Log

class Settings(context: Context) {

    private val share = context.getSharedPreferences("share", Context.MODE_PRIVATE)
    private val editor = share.edit()

    var theme: ThemeManager.Theme
        set(value) {
            editor.putString("Theme", value.toString())
            editor.apply()
        }
        get() = if (share.getString(
                "Theme",
                "LIGHT"
            ) == "LIGHT"
        ) ThemeManager.Theme.LIGHT else ThemeManager.Theme.DARK



}