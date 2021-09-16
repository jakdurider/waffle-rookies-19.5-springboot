package com.wafflestudio.seminar.domain.user.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserDto {
    data class Response(
        var id: Long? = null,
        var name: String = "",
        var email: String = "",
    )

    data class CreateRequest(
        //var id: Long? = null,
        @field:NotBlank
        var name: String = "",
        @field:NotBlank
        var email: String = "",

    )
}
