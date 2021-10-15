package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import com.wafflestudio.seminar.domain.user.model.User
import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class UserDto {
    data class Response(
        val id: Long,
        val name: String,
        val email: String,
        val date_joined: LocalDateTime,
        val participant_profile: ParticipantProfileDto.Response? = null,
        val instructor_profile: InstructorProfileDto.Response? = null,
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            name = user.name,
            date_joined = user.date_joined,
            participant_profile = user.participant_profile?.let{ParticipantProfileDto.Response(it)},
            instructor_profile = user.instructor_profile?.let{InstructorProfileDto.Response(it)}
        )
    }

    data class SignupRequest(
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val password: String,
        @field:NotBlank
        val role: String,

        val university: String = "",
        val accepted: Boolean = true,

        val company: String = "",

        @field:Min(1,message= "Year should be positive integer") // need to be replaced by yearexception
        val year: Int? = null,

    )
    data class ModifyRequest(
        val university: String = "",
        val accepted: Boolean = true,

        val company: String = "",

        @field:Min(1,message= "Year should be positive integer") // need to be replaced by yearexception
        val year: Int? = null,

        )
    data class ParticipantRequest(
        val university: String = "",
        val accepted: Boolean = true,
        )
}