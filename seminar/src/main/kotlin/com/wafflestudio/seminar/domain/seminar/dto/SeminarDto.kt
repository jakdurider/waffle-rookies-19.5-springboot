package com.wafflestudio.seminar.domain.seminar.dto

import com.wafflestudio.seminar.domain.seminar.model.Seminar
import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.model.InstructorProfile
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import org.aspectj.weaver.IntMap
import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class SeminarDto {
    data class Response(
        val id: Long,
        val name: String,
        val capacity: Int,
        val count: Int,
        val time: String,
        val online: String,
        val instructors: List<InstructorResponse> = listOf(),
        val participants: List<ParticipantResponse> = listOf(),
    ){
        constructor(seminar: Seminar): this(
            id = seminar.id,
            name = seminar.name,
            capacity = seminar.capacity,
            count = seminar.count,
            time = seminar.time,
            online = seminar.online,
            instructors = seminar.instructors.map{InstructorResponse(it)},
            participants = seminar.seminarParticipants.map{ParticipantResponse(it)},
        )
    }
    data class InstructorResponse(
        val id: Long?,
        val name: String?,
        val email: String?,
        val company: String,
    ){
        constructor(instructorProfile: InstructorProfile): this(
            id = instructorProfile.user?.id,
            name = instructorProfile.user?.name,
            email = instructorProfile.user?.email,
            company = instructorProfile.company,
        )
    }
    data class ParticipantResponse(
        val id: Long?,
        val name: String?,
        val email: String?,
        val university: String,
        val joined_at: LocalDateTime,
        val is_active: Boolean,
        val dropped_at: LocalDateTime?,
    ){
        constructor(seminarParticipant: SeminarParticipant): this(
            id = seminarParticipant.participant.user!!.id,
            name = seminarParticipant.participant.user!!.name,
            email = seminarParticipant.participant.user!!.email,
            university = seminarParticipant.participant.university,
            joined_at = seminarParticipant.joined_at,
            is_active = seminarParticipant.is_active,
            dropped_at = seminarParticipant.dropped_at,
        )
    }
    data class ManyResponse(
        val id: Long,
        val name: String,
        val instructors: List<InstructorResponse> = listOf(),
        val participant_count: Int,
    ){
        constructor(seminar: Seminar, count: Int): this(
            id = seminar.id,
            name = seminar.name,
            instructors = seminar.instructors.map{InstructorResponse(it)},
            participant_count = count,
        )
    }
    data class CreateRequest(
        @field:NotBlank
        val name: String,

        @field:NotNull
        @field:Min(1,message = "Capacity must be positive integer")
        val capacity: Int,

        @field:NotNull
        @field:Min(1,message = "Count must be positive integer")
        val count: Int,

        @field:NotBlank
        @field:Pattern(regexp="^([0-1][0-9]|2[0-3])([:])([0-5][0-9])$",message="Time should have structure like HH:MM in 24 hour system")
        val time: String,

        @field:Pattern(regexp="^([T,t][R,r][U,u][E,e])|([F,f][A,a][L,l][S,s][E,e])$", message = "Online should be true or false in String format")
        val online: String = "True",

    )
    data class ModifyRequest(
        val name: String? = null,
        @field:Min(1,message = "Capacity must be positive integer")
        val capacity: Int? = null,
        @field:Min(1,message = "Count must be positive integer")
        val count: Int? = null,
        @field:Pattern(regexp="^([0-1][0-9]|2[0-3])([:])([0-5][0-9])$",message="Time should have structure like HH:MM in 24 hour system")
        val time: String? = null,
        @field:Pattern(regexp="^([T,t][R,r][U,u][E,e])|([F,f][A,a][L,l][S,s][E,e])$", message = "Online should be true or false in String format")
        val online: String? = null,
    )
    // when connect user to seminar, use this request with role (instructor or participant)
    data class ConnectingRequest(
        @field:NotBlank
        val role: String,
    )
}