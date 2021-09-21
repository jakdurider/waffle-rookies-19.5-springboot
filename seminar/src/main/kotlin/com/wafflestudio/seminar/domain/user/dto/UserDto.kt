package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.user.model.User
import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class UserDto {
    data class Response(
        val id: Long,
        val email: String,
        val name: String,
        val date_joined: LocalDateTime?,
        val participant_profile: ParticipantDto.Response?,
        val instructor_profile: InstructorDto.Response?,

        ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            name = user.name,
            date_joined = user.date_joined,
            participant_profile = user.participant_profile?.let { ParticipantDto.Response(it) },
            instructor_profile = user.instructor_profile?.let{InstructorDto.Response(it)},
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
        val role : String,

        val university : String = "",
        val accepted : Boolean = true,
        val company : String = "",

        @field:Min(1,message = "Year is less than 1")
        val year : Long? = null,
    )

    data class ModifyRequest(
        val email: String? = null,
        val name: String? = null,

        val university : String = "",
        val accepted : Boolean = true,
        val company : String = "",

        @field:Min(1, message = "Year is less than 1")
        val year : Long? = null,
    )
}