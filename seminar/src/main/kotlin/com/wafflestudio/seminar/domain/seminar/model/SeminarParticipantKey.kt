package com.wafflestudio.seminar.domain.seminar.model

import java.io.Serializable
import javax.persistence.*


@Embeddable
class SeminarParticipantKey : Serializable {
    @Column(name = "participant_id")
    val participantId: Long = 0

    @Column(name = "seminar_id")
    val seminarId: Long = 0
}