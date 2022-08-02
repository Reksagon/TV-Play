package com.tevibox.tvplay.domain.interactor.validator

import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.core.entity.UserCredentials
import com.tevibox.tvplay.core.exception.*
import com.tevibox.tvplay.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignUpValidateUserCredentialsInteractor :
    InteractorInterface<UserCredentials, Flow<UserCredentials>> {

    override suspend fun invoke(param: UserCredentials): Flow<UserCredentials> = flow {
        val exceptionList = mutableListOf<BaseValidationException>()
        if (!StringUtils.isValidEmail(param.email)) exceptionList.add(InvalidEmailException())
        if (!StringUtils.isValidPassword(param.password)) exceptionList.add(InvalidPasswordException())
        if (param.password != param.confirmPassword) exceptionList.add(
            InvalidConfirmPasswordException()
        )
        if (exceptionList.size > 0) throw InvalidCredentialsException(exceptionList)

        emit(param)
    }

}