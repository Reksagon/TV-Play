package com.tevibox.tvplay.data.room.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.data.room.entity.ChannelDbo

class ChannelDboListToStreamListMapper : MapperInterface<List<ChannelDbo>, List<Stream>> {

    override fun transform(param: List<ChannelDbo>) =
        param.map(ChannelDboToStreamMapper()::transform)
}