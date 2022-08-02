package com.tevibox.tvplay.data.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.Streams
import com.tevibox.tvplay.data.retrofit.entity.NetworkStreams

class NetworkStreamsToStreamsMapper : MapperInterface<NetworkStreams, Streams> {

    override fun transform(param: NetworkStreams) = Streams(
        param.error,
        NetworkStreamListToStreamListMapper().transform(param.streams.values),
        param.time
    )
}