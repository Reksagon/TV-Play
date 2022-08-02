package com.tevibox.tvplay.data.mapper

import com.javavirys.core.mapper.MapperInterface
import com.tevibox.tvplay.core.entity.User
import com.tevibox.tvplay.data.retrofit.entity.NetworkUser

class NetworkUserToUserMapper : MapperInterface<NetworkUser, User> {

    override fun transform(param: NetworkUser) = User(
        param.email,
        param.subscriptionStatus,
        param.subscriptionExpire,
        param.card
    )
}