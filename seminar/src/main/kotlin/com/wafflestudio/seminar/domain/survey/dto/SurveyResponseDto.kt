package com.wafflestudio.seminar.domain.survey.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.wafflestudio.seminar.domain.os.dto.OperatingSystemDto
import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.user.model.User
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Max
import javax.validation.constraints.Min


class SurveyResponseDto {
    data class Response(
        var id: Long? = 0,
        var os: OperatingSystem? = null,
        var user: User? = null, // I inserted
        var springExp: Int = 0,
        var rdbExp: Int = 0,
        var programmingExp: Int = 0,
        var major: String? = "",
        var grade: String? = "",
        var backendReason: String? = "",
        var waffleReason: String? = "",
        var somethingToSay: String? = "",
        var timestamp: LocalDateTime? = null
    )

    // TODO: 아래 두 DTO 완성
    data class CreateRequest(
        @field:NotBlank
        var osName: String? = null,

        @field:NotNull
        @field:Min(1,message="springExp should be more than 1")
        @field:Max(5,message="springExp should be less than 5")
        var springExp: Int = 0,

        @field:NotNull
        @field:Min(1,message="rbpExp should be more than 1")
        @field:Max(5,message="rbpExp should be less than 5")
        var rdbExp: Int = 0,

        @field:NotNull
        @field:Min(1,message="programmingExp should be more than 1")
        @field:Max(5,message="programmingExp should be less than 5")
        var programmingExp: Int = 0,

        var major: String? = "",
        var grade: String? = "",
        var backendReason: String? = "",
        var waffleReason: String? = "",
        var somethingToSay: String? = "",
        var timestamp: LocalDateTime? = LocalDateTime.now(),
    )

    data class ModifyRequest(
        var something: String? = ""
        // 예시 - 지우고 새로 생성
    )
}
