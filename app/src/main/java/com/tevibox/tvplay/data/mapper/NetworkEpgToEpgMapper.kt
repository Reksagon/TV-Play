package com.tevibox.tvplay.data.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.Epg
import com.tevibox.tvplay.data.retrofit.entity.NetworkEpg

class NetworkEpgToEpgMapper : MapperInterface<NetworkEpg, Epg> {

    override fun transform(param: NetworkEpg) = Epg(
        param.start,
        param.stop,
        param.title,
        param.description
    )
}