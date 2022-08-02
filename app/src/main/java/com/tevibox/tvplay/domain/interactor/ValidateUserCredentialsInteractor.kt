package com.tevibox.tvplay.domain.interactor

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.core.exception.BaseValidationException
import com.tevibox.tvplay.core.exception.InvalidCredentialsException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateUserCredentialsInteractor :
    InteractorInterface<UserCredentials, Flow<UserCredentials>> {

    override suspend fun invoke(param: UserCredentials): Flow<UserCredentials> = flow {
        val exceptionList = mutableListOf<BaseValidationException>()
//        if (!StringUtils.isValidEmail(param.email)) exceptionList.add(InvalidEmailException())
//        if (!StringUtils.isValidPassword(param.password)) exceptionList.add(InvalidPasswordException())

        if (exceptionList.size > 0) throw InvalidCredentialsException(exceptionList)

        emit(param)
    }

}