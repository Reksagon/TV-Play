package com.tevibox.tvplay.data.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.Stream
import com.tevibox.tvplay.data.retrofit.entity.NetworkStream

class NetworkStreamToStreamMapper(
    private val index: Int
) : MapperInterface<NetworkStream, Stream> {

    override fun transform(param: NetworkStream) = Stream(
        param.id,
        param.name,
        param.logo,
        param.description,
        param.categories ?: listOf(),
        param.epgList?.map(NetworkEpgToEpgMapper()::transform) ?: listOf(),
        param.url,
        index + 1
    )
}