package com.tevibox.tvplay.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tevibox.tvplay.data.room.AppDatabase.Companion.VERSION
import com.tevibox.tvplay.data.room.dao.ChannelDao
import com.tevibox.tvplay.data.room.entity.ChannelDbo

@Database(entities = [ChannelDbo::class], version = VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getChannelDao(): ChannelDao

    companion object {
        const val NAME = "tevibox"
        const val VERSION = 1
    }
}