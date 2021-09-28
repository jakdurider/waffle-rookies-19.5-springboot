package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.seminar.model.SeminarParticipant
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import java.time.LocalDateTime

class ParticipantProfileDto {
    data class Response(
        val id: Long,
        val university: String,
        val accepted: Boolean,
        val seminars: List<SeminarResponse> = listOf(),
    ){
        constructor(participantProfile: ParticipantProfile) : this(
            id = participantProfile.id,
            university = participantProfile.university,
            accepted = participantProfile.accepted,
            seminars = participantProfile.seminars.map{SeminarResponse(it)}
        )
    }
    data class SeminarResponse(
        val id: Long,
        val name: String,
        val joined_at: LocalDateTime,
        val is_active: Boolean,
        val dropped_at: LocalDateTime?,
    ){
        constructor(seminarParticipant : SeminarParticipant): this(
            id = seminarParticipant.seminar!!.id,
            name = seminarParticipant.seminar!!.name,
            joined_at = seminarParticipant.joined_at,
            is_active = seminarParticipant.is_active,
            dropped_at = seminarParticipant.dropped_at,
        )
    }
}