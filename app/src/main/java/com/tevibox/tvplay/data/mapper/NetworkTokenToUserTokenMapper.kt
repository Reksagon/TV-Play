package com.tevibox.tvplay.data.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.UserToken
import com.tevibox.tvplay.data.retrofit.entity.NetworkUserToken

class NetworkTokenToUserTokenMapper : MapperInterface<NetworkUserToken, UserToken> {

    override fun transform(param: NetworkUserToken) =
        UserToken(param.hash, param.expire, param.time)
}