package com.daniyars.translatorapp.local

import android.content.Context
import android.content.SharedPreferences
import com.daniyars.translatorapp.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

const val PREFERENCE = "myPreference"
const val THEME_STATE = "ThemeState"
const val LOG_STATE = "logState"
const val TOTAL_START_TIME = "totalStartTime"
const val TOTAL_TIME = "totalTime"
const val TOTAL_END_TIME = "totalEndTime"
const val USER_NAME = "userName"
const val USER_EMAIL = "userEmail"
const val USER_AVATAR = "userImage"
const val TOTAL_WORDS = "totalWord"
const val INTENT_TO = "intentTo"
const val UID = "userId"
const val USER_LIST = "userList"

class LocalPreferences(context: Context?) {

    private val preference: SharedPreferences? =
        context?.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)

    fun getThemeState(): String? {
        return preference?.getString(THEME_STATE, "white")
    }

    fun setThemeState(state: String) {
        val editor = preference?.edit()
        editor?.putString(THEME_STATE, state)
        editor?.apply()
    }

    fun getLogState(): Boolean? {
        return preference?.getBoolean(LOG_STATE, false)
    }

    fun setLogState(state: Boolean) {
        val editor = preference?.edit()
        editor?.putBoolean(LOG_STATE, state)
        editor?.apply()
    }

    fun setTotalTime(time: String) {
        val editor = preference?.edit()
        editor?.putString(TOTAL_TIME, time)
        editor?.apply()
    }

    fun getTotalTime(): String? {
        return preference?.getString(TOTAL_TIME, "0")
    }

    fun setUserName(name: String) {
        val editor = preference?.edit()
        editor?.putString(USER_NAME, name)
        editor?.apply()
    }

    fun getUserName(): String? {
        return preference?.getString(USER_NAME, "user name")
    }

    fun setUserEmail(email: String) {
        val editor = preference?.edit()
        editor?.putString(USER_EMAIL, email)
        editor?.apply()
    }

    fun getUserEmail(): String? {
        return preference?.getString(USER_EMAIL, "example@google.com")
    }

    fun setUserAvatar(url: String) {
        val editor = preference?.edit()
        editor?.putString(USER_AVATAR, url)
        editor?.apply()
    }

    fun getUserAvatar(): String? {
        return preference?.getString(USER_AVATAR, "")
    }

    fun setTotalWords(words: String) {
        val editor = preference?.edit()
        editor?.putString(TOTAL_WORDS, words)
        editor?.apply()
    }

    fun getTotalWords(): String? {
        return preference?.getString(TOTAL_WORDS, "0")
    }

    fun setTotalEndTime(time: String) {
        val editor = preference?.edit()
        editor?.putString(TOTAL_END_TIME, time)
        editor?.apply()
    }

    fun getTotalEndTime(): String? {
        val start = Calendar.getInstance().timeInMillis
        return preference?.getString(TOTAL_END_TIME, start.toString())
    }

    fun setTotalStartTime(time: String) {
        val editor = preference?.edit()
        editor?.putString(TOTAL_START_TIME, time)
        editor?.apply()
    }

    fun getTotalStartTime(): String? {
        val start = Calendar.getInstance().timeInMillis
        return preference?.getString(TOTAL_START_TIME, start.toString())
    }

    fun setIntent(string: String) {
        val editor = preference?.edit()
        editor?.putString(INTENT_TO, string)
        editor?.apply()
    }

    fun getIntent(): String? {
        return preference?.getString(INTENT_TO, "")
    }

    fun setUID(string: String) {
        val editor = preference?.edit()
        editor?.putString(UID, string)
        editor?.apply()
    }

    fun getUID(): String? {
        return preference?.getString(UID, "")
    }

    fun setUserList(users: List<User>) {
        val editor = preference?.edit()
        val json = Gson().toJson(users)
        editor?.putString(USER_LIST, json)
        editor?.apply()
    }

    fun getUserList(): List<User> {
        val json = preference?.getString(USER_LIST, "")
        val type = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson(json, type)
    }
}
