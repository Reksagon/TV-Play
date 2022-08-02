package com.tevibox.tvplay.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tevibox.tvplay.data.room.entity.ChannelDbo
import com.tevibox.tvplay.data.room.entity.ChannelDbo.Companion.TABLE_NAME

@Dao
interface ChannelDao {

    @Query("select * from $TABLE_NAME")
    suspend fun getChannels(): List<ChannelDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(channel: ChannelDbo)
}