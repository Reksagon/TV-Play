package com.tevibox.tvplay.data.room.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.data.room.entity.ChannelDbo

class ChannelDboToStreamMapper : MapperInterface<ChannelDbo, Stream> {

    override fun transform(param: ChannelDbo) = Stream(
        param.id,
        param.name,
        param.logo,
        "",
        listOf(),
        listOf(),
        "",
        favorite = param.favorite,
        lock = param.lock,
        password = param.password
    )
}