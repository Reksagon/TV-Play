package com.tevibox.tvplay.data.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.data.retrofit.entity.NetworkStream

class NetworkStreamListToStreamListMapper :
    MapperInterface<Collection<NetworkStream>, List<Stream>> {

    override fun transform(param: Collection<NetworkStream>) =
        param.mapIndexed { index, networkStream ->
            NetworkStreamToStreamMapper(index).transform(networkStream)
        }
}