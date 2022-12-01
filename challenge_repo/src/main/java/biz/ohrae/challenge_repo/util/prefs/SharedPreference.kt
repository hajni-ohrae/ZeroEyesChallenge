package biz.ohrae.challenge_repo.util.prefs

import android.content.Context
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.model.user.ZeCustomer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreference @Inject constructor(@ApplicationContext context: Context, private val gson: Gson) {
    companion object {
        const val NAME = "challenge_repo"
    }

    private val preference = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    private val editor = preference.edit()

    private fun setString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    private fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    private fun setFloat(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    private fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun setLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    private fun getString(key: String, defaultValue: String): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return preference.getInt(key, defaultValue)
    }

    private fun getFloat(key: String, defaultValue: Float): Float {
        return preference.getFloat(key, defaultValue)
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preference.getBoolean(key, defaultValue)
    }

    private fun getLong(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    fun setUserData(user: User) {
        setString("user", gson.toJson(user))
    }

    fun getUserData(): User? {
        val json = getString("user", "")
        return if (json.isNotEmpty()) {
            gson.fromJson(json, User::class.java)
        } else {
            null
        }
    }

    fun removeUserData() {
        editor.remove("user")
        editor.commit()
    }

    fun getUid(): String {
        return getString("uid", "")
    }

    fun setUid(uid: String) {
        setString("uid", uid)
    }

    fun setZeCustomer(customer: ZeCustomer) {
        return setString("zeCustomer", gson.toJson(customer))
    }

    fun getZeCustomer(): ZeCustomer? {
        val jsonString = getString("zeCustomer", "")
        if (jsonString.isEmpty()) {
            return null
        }
        return gson.fromJson(jsonString, ZeCustomer::class.java)
    }

    fun setZeServiceIds(serviceIds: List<String>?) {
        if (serviceIds != null) {
            setString("zeServiceIds", gson.toJson(serviceIds))
        } else {
            setString("zeServiceIds", "")
        }
    }

    fun getZeServiceIds(): List<String>? {
        val listString = getString("zeServiceIds", "")
        if (listString.isEmpty()) {
            return null
        }

        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(listString, listType)
    }

    fun setIsFirstLaunch(boolean: Boolean) {
        setBoolean("isFirstLaunch", boolean)
    }

    fun getIsFirstLaunch(): Boolean {
        return getBoolean("isFirstLaunch", true)
    }
}
