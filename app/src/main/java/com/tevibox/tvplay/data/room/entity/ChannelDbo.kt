package com.tevibox.tvplay.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tevibox.tvplay.data.room.entity.ChannelDbo.Companion.LOGO_KEY
import com.tevibox.tvplay.data.room.entity.ChannelDbo.Companion.NAME_KEY
import com.tevibox.tvplay.data.room.entity.ChannelDbo.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, indices = [Index(value = [NAME_KEY, LOGO_KEY], unique = true)])
data class ChannelDbo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = NAME_KEY) val name: String,
    @ColumnInfo(name = LOGO_KEY) val logo: String,
    @ColumnInfo(name = FAVORITE_KEY) val favorite: Boolean,
    @ColumnInfo(name = LOCK_KEY) val lock: Boolean,
    @ColumnInfo(name = PASSWORD_KEY) val password: String
) {

    companion object {
        const val TABLE_NAME = "channels"
        const val NAME_KEY = "name"
        const val LOGO_KEY = "logo"
        const val FAVORITE_KEY = "is_favorite"
        const val LOCK_KEY = "is_lock"
        const val PASSWORD_KEY = "password"
    }
}