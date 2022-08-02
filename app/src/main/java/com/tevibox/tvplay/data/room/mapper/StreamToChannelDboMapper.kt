package com.tevibox.tvplay.data.room.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.data.room.entity.ChannelDbo

class StreamToChannelDboMapper : MapperInterface<Stream, ChannelDbo> {

    override fun transform(param: Stream) = ChannelDbo(
        0,
        param.name,
        param.logo,
        param.favorite,
        param.lock,
        param.password
    )
}