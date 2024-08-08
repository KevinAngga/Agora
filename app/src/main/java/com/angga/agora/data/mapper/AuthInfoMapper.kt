package com.angga.agora.data.mapper

import com.angga.agora.data.dto.AuthInfoDto
import com.angga.agora.domain.model.AuthInfo

fun AuthInfo.toAuthInfoDto() : AuthInfoDto {
    return AuthInfoDto(
        userUID = userUID
    )
}

fun AuthInfoDto.toAuthInfo() : AuthInfo {
    return AuthInfo(
        userUID = userUID
    )
}