package com.b010.reelsblocker

import android.content.Context

object Prefs {
    private const val NAME = "prefs"
    const val INSTAGRAM = "block_instagram"
    const val YOUTUBE = "block_youtube"
    const val COUNT = "block_count"

    private fun sp(c: Context) = c.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    fun get(c: Context, key: String) = sp(c).getBoolean(key, true)
    fun set(c: Context, key: String, v: Boolean) = sp(c).edit().putBoolean(key, v).apply()
    fun count(c: Context) = sp(c).getInt(COUNT, 0)
    fun inc(c: Context) = sp(c).edit().putInt(COUNT, count(c) + 1).apply()
}
