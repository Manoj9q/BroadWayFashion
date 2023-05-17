package com.app.broadwayfashion.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppSharePreferences @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = getSharedPreferenceInstance(context)
    private var sharePreference = "braodway_pref"

    private fun getSharedPreferenceInstance(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            sharePreference, Context.MODE_PRIVATE
        )
    }

    fun getString(key: String, default: String = ""): String {
        return prefs.getString(key, default)!!
    }

    fun setString(key: String, value: String) {
        return prefs.edit().putString(key, value).apply()
    }

    fun <T> putList(key: String?, list: MutableList<T>?) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(key, Gson().toJson(list))
        editor.apply()
    }

    fun <T> getList(key: String?, clazz: Class<T>?): MutableList<T>? {
        val typeOfT: Type = TypeToken.getParameterized(MutableList::class.java, clazz).type
        return Gson().fromJson(getString(key!!, ""), typeOfT)
    }

    fun <T> put(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        prefs.edit().putString(key, jsonString).apply()
    }


    inline fun <reified T> get(key: String): T? {
        val value = getString(key, "")
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun removeUserLogoutData() {
        setString(USER_SELF_ID, "")
        setString(PASSWORD, "")
        setString(USER_FIRST_NAME, "")
        setString(USER_LAST_NAME, "")
        setString(USER_EMAIL, "")
        setString(USER_USER_NAME_ID, "")
    }
}