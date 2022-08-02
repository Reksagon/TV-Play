package com.tevibox.tvplay.data.mapper

import android.content.Context
import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.core.entity.Settings
import com.tevibox.tvplay.data.retrofit.entity.NetworkSettingsAnswer

class NetworkSettingsToSettingsMapper(
    private val context: Context
) : MapperInterface<NetworkSettingsAnswer, Settings> {

    override fun transform(param: NetworkSettingsAnswer) = Settings(
        param.settings.keepAlive,
        param.settings.streamsRefresh,
        param.settings.settingsRefresh,
        transformCategories(param.settings.categories),
        param.settings.appLatestVersion,
        param.settings.appUrl
    )

    private fun transformCategories(categories: List<String>): List<Category> {
        val result = mutableListOf<Category>()
        result.add(Category(0, context.getString(R.string.all)))
        categories.forEachIndexed { index, name ->
            result.add(Category(index + 1, name))
        }
        return result
    }
}