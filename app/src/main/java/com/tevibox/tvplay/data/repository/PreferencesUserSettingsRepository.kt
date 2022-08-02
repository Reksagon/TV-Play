package com.tevibox.tvplay.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.tevibox.tvplay.core.entity.UserSettings
import com.tevibox.tvplay.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.flow

class PreferencesUserSettingsRepository(
    private val preferences: SharedPreferences
) : UserSettingsRepository {

    override suspend fun get() = flow {
        val category = preferences.getString(CATEGORY_NAME, "")!!
        val channelName = preferences.getString(CHANNEL_NAME, "")!!
        val channelUrl = preferences.getString(CHANNEL_URL, "")!!

        val tokenHash = preferences.getString(TOKEN_HASH_KEY, "")!!
        val tokenExpire = preferences.getLong(TOKEN_EXPIRE_KEY, 0L)
        val tokenTime = preferences.getLong(TOKEN_TIME_KEY, 0L)
        emit(UserSettings(category, channelName, channelUrl, tokenHash, tokenExpire, tokenTime))
    }

    override suspend fun set(settings: UserSettings) {
        preferences.edit {
            putString(CATEGORY_NAME, settings.categoryName)
            putString(CHANNEL_NAME, settings.channelName)
            putString(CHANNEL_URL, settings.channelUrl)

            putString(TOKEN_HASH_KEY, settings.tokenHash)
            putLong(TOKEN_EXPIRE_KEY, settings.tokenExpire)
            putLong(TOKEN_TIME_KEY, settings.tokenTime)
        }
    }

    companion object {
        private const val CATEGORY_NAME = "CATEGORY_NAME"
        private const val CHANNEL_NAME = "CHANNEL_NAME"
        private const val CHANNEL_URL = "CHANNEL_URL"

        private const val TOKEN_HASH_KEY = "TOKEN_HASH_KEY"
        private const val TOKEN_EXPIRE_KEY = "TOKEN_EXPIRE_KEY"
        private const val TOKEN_TIME_KEY = "TOKEN_TIME_KEY"
    }
}