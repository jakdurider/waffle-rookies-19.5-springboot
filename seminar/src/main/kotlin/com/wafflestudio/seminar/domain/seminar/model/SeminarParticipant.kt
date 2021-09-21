package com.wafflestudio.seminar.domain.seminar.model

import com.wafflestudio.seminar.domain.model.BaseTimeEntity
import com.wafflestudio.seminar.domain.os.model.OperatingSystem
import com.wafflestudio.seminar.domain.user.model.ParticipantProfile
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class SeminarParticipant (
    @EmbeddedId
    val seminarParticipantKey : SeminarParticipantKey,

    @field:NotNull
    val joined_at : LocalDateTime = LocalDateTime.now(),

    @field:NotNull
    val is_active : Boolean,

    val dropped_at : LocalDateTime? = null,

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id", referencedColumnName = "id", nullable = true)
    val participantProfile : ParticipantProfile?,

    @ManyToOne
    @MapsId("seminarId")
    @JoinColumn(name = "seminar_id", referencedColumnName = "id", nullable = true)
    val seminar : Seminar?,

    ) : BaseTimeEntity()