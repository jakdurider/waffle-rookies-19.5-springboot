package com.wafflestudio.seminar.domain.user.dto

import com.wafflestudio.seminar.domain.seminar.dto.SeminarDto
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile

class ParticipantDto
{
    data class Response(
        val id: Long,
        val university: String,
        val accepted: Boolean,
        val seminars: List<SeminarDto.ParticipantResponse>?
        ) { constructor(participant: ParticipantProfile) : this(
                id = participant.id,
                university = participant.university,
                accepted = participant.accepted,
                seminars = participant.seminars?.map { SeminarDto.ParticipantResponse(it,) }
        )
    }
}