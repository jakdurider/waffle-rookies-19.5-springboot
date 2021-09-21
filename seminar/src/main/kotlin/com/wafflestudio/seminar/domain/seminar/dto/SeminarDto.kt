package com.wafflestudio.seminar.domain.seminar.dto

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import java.time.LocalDateTime
import javax.validation.constraints.*

class SeminarDto {
    data class CreateRequest(
        @field:NotBlank(message="Register with name")
        val name: String,

        @field:NotNull(message="Register with capacity")
        @field:Min(1,message = "capacity is lower than 1")
        val capacity: Long,

        @field:NotNull(message="Register with count")
        @field:Min(1,message = "count is lower than 1")
        val count: Long,

        @field:NotBlank(message="Register with time")
        @field:Pattern(regexp="^([0-1][0-9]|2[0-3])([:])([0-5][0-9])$",message="time should have structure like HH:MM in 24 hour system")
        val time: String,

        @field:Pattern(regexp="^([T,t][R,r][U,u][E,e])|([F,f][A,a][L,l][S,s][E,e])$", message = "")
        val online: String = "True",

    )
    data class ParticipantResponse(
        val id: Long,
        val name: String,
        val joined_at: LocalDateTime,
        val is_active: Boolean,
        val dropped_at : LocalDateTime,
    ){ constructor(seminar: Seminar) : this(
        id = seminar.id,
        name = seminar.name,

    )
    }
    data class InstructorResponse(
        val id: Long,
        val name: String,

    ){ constructor(seminar: Seminar) : this(
        id = seminar.id,
        name = seminar.name
    )
    }
}