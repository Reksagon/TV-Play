package com.tevibox.tvplay.core.exception

enum class ResponseExceptionCodesEnum(
    val code: Int
) {

    NO_ACTIVE_SUBSCRIPTION(6001),

    NO_DEFINED_CARD_NUMBER(5001), // No defined card number
    INVALID_CARD(5002) // Invalid card
}